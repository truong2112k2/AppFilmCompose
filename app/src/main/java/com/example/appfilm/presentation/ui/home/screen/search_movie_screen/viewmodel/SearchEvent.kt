package com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class SearchEvent {

    data object GetMovies : SearchEvent()
    data class GetMoviesSearch(val keyword: String) : SearchEvent()
    data object ResetSearchState : SearchEvent()

}