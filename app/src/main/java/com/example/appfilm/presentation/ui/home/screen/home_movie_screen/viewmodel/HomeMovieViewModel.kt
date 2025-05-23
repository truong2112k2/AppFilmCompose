package com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeMovieViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    @ApplicationContext val context: Context
) : ViewModel() {
    private val _getNewMovieState = MutableStateFlow(UIState())
    val getNewMovieState: StateFlow<UIState> = _getNewMovieState


    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies


    init {
        fetchMoviesIfNeeded()
    }

    private fun fetchMoviesIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_movies.value.isEmpty()) {

                getMoviesNoNetwork() // lay du lieu tu local truoc

                delay(2000)

                if (isNetworkAvailable(context)) {
                    getMoviesOnNetwork(context, 1) // sau do moi tai du lieu moi ve, neu co mang
                }


            }
        }

    }

    private fun getMoviesNoNetwork() {

        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getMoviesUseCase.getMovie().collect { movies ->
                if (movies.isEmpty()) {

                    Log.d(Constants.ERROR_TAG, "Error: CAN'T GET DATA FROM DATABASE")

                } else {

                    _movies.value = movies
                }
            }
        }
    }


    private fun getMoviesOnNetwork(context: Context, page: Int) {
        _getNewMovieState.value = UIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                appUseCases.fetchDataAndSaveFromDbUseCase.fetchDataMovieAndSaveFromDb(context, page)

            if (result.data?.isNotEmpty() == true) {

                _movies.value = result.data
                _getNewMovieState.value = UIState(isSuccess = true)
                Log.d(Constants.STATUS_TAG, "fetchDataAndSaveFromDb UseCase success")


            } else {

                _getNewMovieState.value = UIState(error = result.exception?.let {
                    convertGetMoviesException(it)
                })


            }
        }
    }

    private val _addFavouriteMovie = MutableStateFlow(UIState())
    val addFavouriteMovie: StateFlow<UIState> = _addFavouriteMovie

    private fun addFavouriteMovie(movie: Movie) {

        viewModelScope.launch(Dispatchers.IO) {

            appUseCases.addFavouriteMovieUseCase.invoke(movie).collect { result ->

                _addFavouriteMovie.value = when (result) {
                    is Resource.Success -> {
                        Log.d(Constants.STATUS_TAG, "Success add ${movie.name} favourite")
                        UIState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d(Constants.STATUS_TAG, "Error add favourite ${result.message}")
                        UIState(error = result.message)
                    }

                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Loading add favourite")
                        UIState(isLoading = true)
                    }
                }

            }

        }
    }
    private val _checkFavourite = MutableStateFlow<Boolean>(false)
    val checkFavourite: StateFlow<Boolean> = _checkFavourite.asStateFlow()

    private fun checkFavouriteMovie(movieId: String) {

        Log.d("3334", "id Movie by HomeMovieViewModel  $movieId")

        viewModelScope.launch(Dispatchers.IO) {
            val result = appUseCases.checkFavouriteMovieUseCase.invoke(movieId)
            _checkFavourite.value = result.data == true

            Log.d("3334", "result by HomeMovieViewModel  ${result.data}")

        }
    }

    fun handleEvent(homeMovieEvent: HomeMovieEvent) {
        when (homeMovieEvent) {
            is HomeMovieEvent.GetMoviesOnNetwork -> {
                getMoviesOnNetwork(homeMovieEvent.context, homeMovieEvent.page)
            }

            is HomeMovieEvent.AddFavouriteMovie -> {
                addFavouriteMovie(homeMovieEvent.movie)
            }

            is HomeMovieEvent.CheckFavouriteMovie -> {checkFavouriteMovie(homeMovieEvent.movieId)}
        }
    }

    private fun convertGetMoviesException(exception: Exception): String {
        return when (exception) {
            is IOException -> {
                "Cannot connect to the internet. Please check your network connection."
            }

            is HttpException -> {
                when (exception.code()) {
                    400 -> "Bad request. Please try again."
                    401 -> "Unauthorized access. Please log in again."
                    403 -> "Access denied."
                    404 -> "Requested content not found."
                    500 -> "Internal server error. Please try again later."
                    else -> "Server error: ${exception.code()} - ${exception.message()}"
                }
            }

            else -> {
                "An unexpected error occurred. Please try again."
            }
        }
    }
}