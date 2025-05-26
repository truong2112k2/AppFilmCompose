package com.example.appfilm.presentation.ui.home.screen.home_movie_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.CustomTextClickable
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.HomeMovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.NewMovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieEvent


@Composable
fun HomeMovieScreen(
    navController: NavController,
    context: Context,
    getNewMovieState: UIState,

    movies: List<Movie>,

    onEventClick: (HomeMovieEvent) -> Unit
) {


//    if (getNewMovieState.error?.isNotEmpty() == true) {
//        CustomRetryBox(onClick = {
//            onEventClick(HomeMovieEvent.GetMoviesOnNetwork(context, 1))
//        })
//
//    } else {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (movies.isNotEmpty()) {
            val newMovie = movies[0]

            item {

                if (getNewMovieState.isLoading) {

                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Log.d("312312", "Vao Day r")
                        Text(
                            "Updating new movies",
                            color = Color.White
                        )
                        Spacer(Modifier.width(6.dp))
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                NewMovieItem(
                    newMovie,
                    onClickPlay = {
                        navController.navigate("DETAIL_ROUTE/${newMovie.slug}")
                    },


                    )
            }


            item {
                Spacer(modifier = Modifier.height(8.dp))
            }



            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomTextTitle("New Movies")
                    Spacer(Modifier.weight(1f))
                    CustomTextClickable(
                        onClick = { navController.navigate(Constants.CATEGORY_ROUTE) }
                    )
                }
            }

            item {


                LazyRow {
                    items(movies.drop(1)) { movie ->
                        HomeMovieItem(
                            movie,
                            onClick = {
                                navController.navigate(
                                    "DETAIL_ROUTE/${movie.slug}"
                                )

                            }
                        )
                    }
                }
            }


        }
    }


//


    //}
}


