package com.example.appfilm.presentation.ui.home.screen.home_movie_screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.commandiron.compose_loading.CubeGrid
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.MovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.NewMovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieViewModel


@Composable
fun HomeMovieScreen(navController: NavController, context: Context, homeMovieViewModel: HomeMovieViewModel = hiltViewModel()) {


    val getNewMovieState by homeMovieViewModel.getNewMovieState.collectAsState()
    val movies by homeMovieViewModel.movies.collectAsState()
    var isShowSeeMore by rememberSaveable { mutableStateOf(false) }







    if(getNewMovieState.error?.isNotEmpty() == true){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(getNewMovieState.error.toString() , style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 18.sp, color = Color.White.copy(alpha = 0.8f),
                lineHeight = 20.sp
            ), modifier = Modifier.padding(8.dp), textAlign = TextAlign.Center)

            Button(onClick = {
                homeMovieViewModel.getMoviesOnNetwork(context, 1)

            }) {
                Text("Re Try")
            }
        }


    }else{


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if(getNewMovieState.isLoading) {
              item {

                  CubeGrid(color = Color.LightGray,
                     size =  DpSize(100.dp, 100.dp),

                  )
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
                        onClickAddFavourite = {}
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    if(isShowSeeMore){
                        AnimatedVisibility(
                            visible = isShowSeeMore,
                            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                        ) {
                            Text(
                                text = "Swipe to find more",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                style = TextStyle(
                                    textAlign = TextAlign.End,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                        }
                    }

                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                        ,
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomTextTitle("New Movies")
                        Spacer(Modifier.weight(1f))
                        CustomTextClickable(
                            onClick = { navController.navigate(Constants.CATEGORY_ROUTE)}
                        )
                    }
                }

                item {



                  LazyRow() {
                      items(movies.drop(1)) { movie ->
                          MovieItem(
                              movie,
                              onClick = {
                                  navController.navigate("DETAIL_ROUTE/${movie.slug}"
                                  )

                              }
                              )
                      }
                  }
                }



            }
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
