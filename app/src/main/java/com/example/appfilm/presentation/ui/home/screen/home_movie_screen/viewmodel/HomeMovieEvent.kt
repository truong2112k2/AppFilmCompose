package com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel

import android.content.Context
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.presentation.ui.home.viewmodel.HomeEvent

sealed class HomeMovieEvent(){
    data class GetMoviesOnNetwork(val context: Context, val page: Int ) : HomeMovieEvent()
    data class AddFavouriteMovie(val movie: Movie): HomeMovieEvent()

}