package com.example.appfilm.presentation.ui.home.screen.home_movie_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.presentation.ui.CustomConfirmDialog
import com.example.appfilm.presentation.ui.CustomRetryBox
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.MovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.NewMovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieEvent
import com.example.appfilm.presentation.ui.home.viewmodel.HomeUIState


@Composable
fun HomeMovieScreen(
    navController: NavController,
    context: Context,
    getNewMovieState: HomeUIState,
    addFavouriteMovieState: HomeUIState,
    movies: List<Movie>,
    onEventClick: (HomeMovieEvent) -> Unit
) {


    var isShowSeeMore by rememberSaveable { mutableStateOf(false) }



    var errorAddFavourite by rememberSaveable {  mutableStateOf<String?>(null)  }




    if (getNewMovieState.error?.isNotEmpty() == true) {

        CustomRetryBox(onClick = {
            onEventClick(HomeMovieEvent.GetMoviesOnNetwork(context, 1))
        })

    } else {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            item {
                if (getNewMovieState.isLoading) {


                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

            }


            if (movies.isNotEmpty()) {
                val newMovie = movies[0]

                item {
                    NewMovieItem(
                        newMovie,
                        onClickPlay = {
                            navController.navigate("DETAIL_ROUTE/${newMovie.slug}")
                        },
                        onClickAddFavourite = {
                            onEventClick(HomeMovieEvent.AddFavouriteMovie(newMovie))

                        },
                         addFavouriteMovieState,

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


                    LazyRow() {
                        items(movies.drop(1)) { movie ->
                            MovieItem(
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


        var isShowDialogConfirm by rememberSaveable { mutableStateOf(false) }



        if (isShowDialogConfirm) {
            AlertDialog(
                onDismissRequest = {isShowDialogConfirm = false},
                title = {
                    Text(text = errorAddFavourite.toString())
                },
                confirmButton = {
                    TextButton(onClick = {isShowDialogConfirm = false }) {
                        Text("Ok")
                    }
                },

            )
        }



    }
}


@Composable
fun CustomTextClickable(onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100),
        label = "scaleAnim"
    )

    Text(
        text = "Find More",
        color = Color.White,
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            }
    )
}
