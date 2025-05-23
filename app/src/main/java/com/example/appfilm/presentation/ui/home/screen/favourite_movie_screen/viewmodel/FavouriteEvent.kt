package com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel

sealed class FavouriteEvent {
    data object GetFavouriteMovies : FavouriteEvent()
}