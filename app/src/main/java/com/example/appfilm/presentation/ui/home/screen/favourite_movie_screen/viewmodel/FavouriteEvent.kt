package com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import kotlinx.coroutines.launch

sealed class FavouriteEvent {
    data object GetFavouriteMovies : FavouriteEvent()


}