package com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.home.viewmodel.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    val appUseCases: AppUseCases
) : ViewModel() {


    private val _listMovieSearch = MutableStateFlow<List<MovieBySearch>>(emptyList<MovieBySearch>())
    val listMovieSearch = _listMovieSearch.asStateFlow()


    private val _listMovie = MutableStateFlow<List<Movie>>(emptyList<Movie>())
    val listMovie = _listMovie.asStateFlow()

    private val _searchMoviesState = MutableStateFlow(SearchUiState())
    val searchMoviesState = _searchMoviesState.asStateFlow()

    private fun getMovies() {
        if( _listMovie.value.isEmpty()){
            viewModelScope.launch(Dispatchers.IO) {
                appUseCases.getMoviesUseCase.getMovie().collect { movies ->
                    if (movies.isNotEmpty()) {
                        _listMovie.value = movies
                    }
                }
            }
        }
    }


    private fun getMoviesSearch(keyword: String) {


        viewModelScope.launch(Dispatchers.IO) {
            _searchMoviesState.value = SearchUiState(isLoading = true)

            if (keyword.isEmpty()) {
                _searchMoviesState.value = SearchUiState(error = "Input name movie, please!")

                Log.d("SearchMovieViewModel","keyword.isEmpty()")
                return@launch
            }
            val searchMovies = appUseCases.searchMoviesUseCase.invoke(keyword)
            _searchMoviesState.value = when (searchMovies) {
                is Resource.Error -> {
                    Log.d("SearchMovieViewModel","Error getMoviesSearch  ${searchMovies.message}")

                    SearchUiState(error = searchMovies.message)

                }

                is Resource.Loading -> TODO()
                is Resource.Success -> {

                    if (searchMovies.data != null) {

                        if(searchMovies.data.isEmpty()){
                            _searchMoviesState.value = SearchUiState(error = "No result!")
                            Log.d(Constants.STATUS_TAG, "No result!")
                            return@launch


                        }
                        _listMovieSearch.value = searchMovies.data
                        Log.d(Constants.STATUS_TAG, "searchMovies movies by SearchMovieViewModel success ${searchMovies.data.size}")

                        Log.d(Constants.STATUS_TAG, "_listMovieSearch movies by SearchMovieViewModel success ${_listMovieSearch.value.size}")


                    }

                    SearchUiState(isSuccess = true)
                }
            }
        }
    }

    private fun resetSearchState(){
        _searchMoviesState.value = SearchUiState()
    }


    fun handleEvent(searchEvent: SearchEvent) {
        when (searchEvent) {
            is SearchEvent.GetMovies -> {
                getMovies()
            }

            is SearchEvent.GetMoviesSearch -> {
                getMoviesSearch(searchEvent.keyword)
            }

            SearchEvent.ResetSearchState -> {resetSearchState()}
        }
    }

}