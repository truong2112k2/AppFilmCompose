package com.example.appfilm.presentation.ui.home.screen.home_movie_screen

import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.CustomButtonWithIcon
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.MovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.NewMovieItem
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieViewModel
import com.example.appfilm.presentation.ui.isNetworkAvailable


@Composable
fun HomeMovieScreen(navController: NavController, context: Context, homeMovieViewModel: HomeMovieViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        if (isNetworkAvailable(context)) {
            homeMovieViewModel.getMoviesOnNetwork(context, 1)
            Log.d(Constants.STATUS_TAG, "Use Internet")
        } else {
            Log.d(Constants.STATUS_TAG, "Use Local")
            homeMovieViewModel.getMoviesNoNetwork()

        }
    }



    val getNewMovieState by homeMovieViewModel.getNewMovieState.collectAsState()
    val movies by homeMovieViewModel.movies.collectAsState()
    var isShowSeeMore by rememberSaveable { mutableStateOf(false) }






    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 200f, translateAnim + 200f)
    )

    CustomLoadingDialog(getNewMovieState.isLoading)

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
            if (movies.isNotEmpty()) {
                val newMovie = movies[0]

                item {
                    NewMovieItem(newMovie, brush)
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
                    val listState = rememberLazyListState()

                    // Kiểm tra nếu user lướt đến cuối danh sách
                    LaunchedEffect(listState) {
                        snapshotFlow {
                            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                        }.collect { lastVisibleIndex ->
                            if (lastVisibleIndex != null) {
                                if(lastVisibleIndex >= movies.drop(1).lastIndex - 3){
                                    isShowSeeMore = true
                                }else{
                                    isShowSeeMore = false
                                }

                                if (lastVisibleIndex == movies.drop(1).lastIndex) {

                                    navController.navigate(Constants.CATEGORY_ROUTE) // thay bằng route thật
                                }
                            }

                        }
                    }
                  LazyRow(state = listState) {
                      items(movies.drop(1)) { movie ->
                          MovieItem(movie, brush)
                      }
                  }
                }



            }
        }
    }
}





