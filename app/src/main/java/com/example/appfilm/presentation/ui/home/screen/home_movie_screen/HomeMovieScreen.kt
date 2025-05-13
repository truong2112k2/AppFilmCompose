package com.example.appfilm.presentation.ui.home.screen.home_movie_screen

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
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
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieViewModel
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(540.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.White, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        var isImageLoaded by remember { mutableStateOf(false) }

                        SubcomposeAsyncImage(
                            model = newMovie.poster_url,
                            contentDescription = newMovie.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop,
                            loading = {
                                isImageLoaded = false
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(brush)
                                )
                            },
                            success = {
                                isImageLoaded = true
                                SubcomposeAsyncImageContent()
                            },
                            error = {
                                isImageLoaded = false
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(imageVector = Icons.Default.Warning, contentDescription = "")
                                }
                            }
                        )

                        if (isImageLoaded) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black.copy(0.6f))
                                        )
                                    )
                            )

                            Column(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    newMovie.name.toString(),
                                    style = TextStyle(
                                        fontFamily = Constants.MyFontFamily,
                                        fontSize = 35.sp,
                                        color = Color.White.copy(alpha = 0.8f)
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Spacer(Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomButtonWithIcon(onClick = {}, Icons.Default.PlayArrow, "Play")
                                    Spacer(Modifier.width(8.dp))
                                    CustomButtonWithIcon(onClick = {}, Icons.Default.Favorite, "Save to favourite")
                                }
                                Spacer(Modifier.height(30.dp))
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 5000.dp) // Giới hạn chiều cao
                    ) {
                        items(movies.drop(1)) { movie ->
                            MovieItem(movie, brush)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    brush: Brush
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(4.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
    ) {
        var isImageLoaded by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(
            model = movie.poster_url,
            contentDescription = movie.name,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            loading = {
                isImageLoaded = false
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(brush)
                )
            },
            success = {
                isImageLoaded = true
                SubcomposeAsyncImageContent()
            },
            error = {
                isImageLoaded = false
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = Icons.Default.Warning,
                        contentDescription = ""
                    )
                }
            }
        )

        if (isImageLoaded) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(0.8f)
                            )
                        )
                    )
            )

            Text(
                text = movie.name.toString(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .basicMarquee(
                        animationMode = MarqueeAnimationMode.Immediately,
                        repeatDelayMillis = 1800,
                        spacing = MarqueeSpacing(48.dp),
                        velocity = 30.dp
                    ),
                color = Color.White.copy(0.8f)
            )
        }
    }
}