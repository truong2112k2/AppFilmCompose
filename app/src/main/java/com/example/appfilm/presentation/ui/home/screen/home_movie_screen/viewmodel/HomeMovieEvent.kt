package com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel

import android.content.Context

sealed class HomeMovieEvent(){
    data class GetMoviesOnNetwork(val context: Context, val page: Int ) : HomeMovieEvent()
}