package com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.home.viewmodel.HomeUIState
import com.example.appfilm.presentation.ui.isNetworkAvailable
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeMovieViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
    @ApplicationContext val context: Context
): ViewModel() {
    private val _getNewMovieState = MutableStateFlow(HomeUIState())
    val getNewMovieState : StateFlow<HomeUIState> = _getNewMovieState


    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies : StateFlow<List<Movie>> = _movies


    init {
        fetchMoviesIfNeeded()
    }

    private fun fetchMoviesIfNeeded() {
        if (_movies.value.isEmpty()) {
            if (isNetworkAvailable(context)) {
                getMoviesOnNetwork(context, 1)
            } else {
                getMoviesNoNetwork()
            }
        }
    }

    private fun getMoviesNoNetwork() {

        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.getMoviesUseCase.getMovie().collect{movies ->
                if (movies.isEmpty()) {

                    Log.d(Constants.ERROR_TAG,"Error: CAN'T GET DATA FROM DATABASE")

                } else {

                    _movies.value = movies
                }
            }
        }
    }




    fun getMoviesOnNetwork(context: Context, page: Int ){
        _getNewMovieState.value = HomeUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result =  appUseCases.fetchDataAndSaveFromDbUseCase.fetchDataMovieAndSaveFromDb(context, page)

            if(result.data?.isNotEmpty() == true){

                _movies.value = result.data
                _getNewMovieState.value = HomeUIState(isSuccess = true)
                Log.d(Constants.STATUS_TAG,"fetchDataAndSaveFromDb UseCase success")


            }else{

                _getNewMovieState.value = HomeUIState(error = result.exception?.let {
                    convertGetMoviesException(it)
                })


            }
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

    fun handleEvent(homeMovieEvent: HomeMovieEvent){
        when(homeMovieEvent){
            is HomeMovieEvent.GetMoviesOnNetwork -> {getMoviesOnNetwork(homeMovieEvent.context, homeMovieEvent.page)}
        }
    }

}