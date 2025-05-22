package com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.usecase.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val appUseCases: AppUseCases,
) : ViewModel() {

    private val _getMoviesFavouriteState = MutableStateFlow<FavouriteUiState>(FavouriteUiState())
    val getMoviesFavouriteState: StateFlow<FavouriteUiState> = _getMoviesFavouriteState

    private val _listMovies = MutableStateFlow<List<Movie>>(emptyList())
    val listMovies = _listMovies.asStateFlow()





    private fun getFavouriteMovies() {
        viewModelScope.launch() {
            if(_listMovies.value.isEmpty()){
                appUseCases.getFavouriteMoviesUseCase.invoke().collect{

                    _getMoviesFavouriteState.value =  when(it){
                        is Resource.Loading -> {
                            Log.d("getFavouriteMovies", "getFavouriteMovies loading ")

                            FavouriteUiState(isLoading = true)
                        }
                        is Resource.Error -> {
                            Log.d("getFavouriteMovies", "getFavouriteMovies error ${it.message} ")
                            FavouriteUiState(error = it.message)
                        }
                        is Resource.Success ->{
                            Log.d("getFavouriteMovies", "getFavouriteMovies isSuccess ")
                            if(it.data == null ){
                                return@collect
                            }
                            _listMovies.value = it.data
                            Log.d("getFavouriteMovies", "getFavouriteMovies size ${_listMovies.value.size} ")

                            FavouriteUiState(isSuccess = true)

                        }
                    }
                }
            }

        }
    }



    fun handleEvent(favouriteEvent: FavouriteEvent) {
        when (favouriteEvent) {
            FavouriteEvent.GetFavouriteMovies -> {
                getFavouriteMovies()
            }
        }
    }


}