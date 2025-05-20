package com.example.appfilm.presentation.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.detail_movie.MovieDetail
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

    fun getDetailMovie(slug: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

        }

    }


    fun onEvent(action: DetailEvent) {
        when (action) {
            is DetailEvent.GetDetail -> getDetailMovie(action.slug)
            is DetailEvent.ReTry -> getDetailMovie(action.slug)
        }
    }
}