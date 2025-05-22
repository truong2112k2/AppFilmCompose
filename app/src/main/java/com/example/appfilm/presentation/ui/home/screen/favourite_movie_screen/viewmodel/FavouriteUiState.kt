package com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel


data class FavouriteUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
) {
}