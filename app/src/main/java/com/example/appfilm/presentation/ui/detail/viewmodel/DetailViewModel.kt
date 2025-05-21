package com.example.appfilm.presentation.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.example.appfilm.domain.toMovie
import com.example.appfilm.domain.usecase.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel() {

    private val _getDetailMovieState = MutableStateFlow<DetailUiState>(DetailUiState())
    val getDetailMovieState: StateFlow<DetailUiState> = _getDetailMovieState.asStateFlow()

    private val _detailMovie = MutableStateFlow<MovieDetail>(MovieDetail())
    val detailMovie: StateFlow<MovieDetail> = _detailMovie.asStateFlow()

    private fun getDetailMovie(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {

            if(detailMovie.value.listEpisodeMovie?.isEmpty() == true){

                _getDetailMovieState.value = DetailUiState(isLoading = true)
                Log.d("check", _getDetailMovieState.value.isLoading.toString())
                val getDetailMove = appUseCases.fetchDetailMovie.invoke(slug)
                _getDetailMovieState.value = when (getDetailMove) {
                    is Resource.Loading -> {
                        DetailUiState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d(Constants.STATUS_TAG, "Success from getDetailMovie ")

                        _detailMovie.value = getDetailMove.data!!



                        DetailUiState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d(Constants.STATUS_TAG, "Error from getDetailMovie ")
                        _detailMovie.value = MovieDetail()

                        DetailUiState(error = getDetailMove.message)
                    }
                }
            }else{
                Log.d(Constants.STATUS_TAG,"detail movie is not null")
            }





        }

    }
    private fun retryGetDetailMovie(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {


                _getDetailMovieState.value = DetailUiState(isLoading = true)

                val getDetailMove = appUseCases.fetchDetailMovie.invoke(slug)
                _getDetailMovieState.value = when (getDetailMove) {
                    is Resource.Loading -> {
                        DetailUiState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d("312321", "Get Data from retry is success ")

                        _detailMovie.value = getDetailMove.data!!



                        DetailUiState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d("312321", "Error Get Data from retry is success ")
                        _detailMovie.value = MovieDetail()

                        DetailUiState(error = getDetailMove.message)
                    }
                }
        }
    }
    private val _addFavouriteMovieState = MutableStateFlow<DetailUiState>(DetailUiState())
    val addFavouriteMovieState: StateFlow<DetailUiState> = _addFavouriteMovieState.asStateFlow()
    private fun addFavouriteMovie(movieDetail: MovieDetail) {


        val movie = movieDetail.toMovie()
        viewModelScope.launch(Dispatchers.IO) {

            appUseCases.addFavouriteMovieUseCase.invoke(movie).collect { result ->

                _addFavouriteMovieState.value = when (result) {
                    is Resource.Success -> {
                        Log.d(Constants.STATUS_TAG, "Success add detail ${movie.name} favourite")
                        DetailUiState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d(Constants.STATUS_TAG, "Error add detail favourite ${result.message}")
                        DetailUiState(error = result.message)
                    }

                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Loading detail add favourite")
                        DetailUiState(isLoading = true)
                    }
                }

            }

        }
    }
    private val _checkFavourite = MutableStateFlow<Boolean>(false)
    val checkFavourite: StateFlow<Boolean> = _checkFavourite.asStateFlow()

    fun checkFavouriteMovie(movieId: String) {

        Log.d("kkkk", "id Movie in checkFavouriteMovie  ${movieId.toString()}")

        viewModelScope.launch(Dispatchers.IO) {
            val result = appUseCases.checkFavouriteMovieUseCase.invoke(movieId)
            if(result.data == true){


                _checkFavourite.value = true
            }else{
                _checkFavourite.value = false
            }

            Log.d("kkkk", "result in checkFavouriteMovie  ${result.data}")

        }
    }
    fun onEvent(action: DetailEvent) {
        when (action) {
            is DetailEvent.GetDetail -> getDetailMovie(action.slug)
            is DetailEvent.ReTry -> retryGetDetailMovie(action.slug)
            is DetailEvent.AddFavouriteMovie -> addFavouriteMovie(action.movieDetail)
            is DetailEvent.CheckFavouriteMovie -> {checkFavouriteMovie(action.movieID)}


        }
    }
}