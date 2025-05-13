package com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel

@Composable
fun FavouriteMovieScreen(homeViewModel: HomeViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text("FavouriteMovieScreen")

    }

}