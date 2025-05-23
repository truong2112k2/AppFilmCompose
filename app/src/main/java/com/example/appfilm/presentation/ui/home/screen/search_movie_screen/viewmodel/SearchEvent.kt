package com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel

sealed class SearchEvent {

    data object GetMovies : SearchEvent()
    data class GetMoviesSearch(val keyword: String) : SearchEvent()
    data object ResetSearchState : SearchEvent()

}