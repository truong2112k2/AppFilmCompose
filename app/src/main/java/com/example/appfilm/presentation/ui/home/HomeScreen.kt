package com.example.appfilm.presentation.ui.home

import android.app.AlertDialog
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

import com.example.appfilm.R
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.home.components.CustomModalNavigationDrawer
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel
import com.example.appfilm.presentation.ui.register.componets.CustomSuccessDialog
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.spr.jetpack_loading.components.indicators.BallBeatIndicator
import com.spr.jetpack_loading.components.indicators.CubeTransitionIndicator
import com.spr.jetpack_loading.components.indicators.PacmanIndicator
import com.spr.jetpack_loading.components.indicators.lineScaleIndicator.LineScaleIndicator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,

    homeViewModel: HomeViewModel = hiltViewModel()

) {

    LaunchedEffect(Unit) {
        homeViewModel.getNewMovie(3)
    }




    CustomModalNavigationDrawer(homeViewModel)


}


@Composable
fun HomeMovieScreen(homeViewModel: HomeViewModel) {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val getNewMovieState by homeViewModel.getNewMovieState.collectAsState()
        val movies by homeViewModel.movies.collectAsState()


    LoadingScreen(getNewMovieState.isLoading)



            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                items(movies) { movie ->



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(4.dp)
                            .shadow(8.dp, RoundedCornerShape(12.dp)) // 👈 Bóng đổ
                            .clip(RoundedCornerShape(8.dp)),
                    ) {

                        AsyncImage(
                            model = movie.poster_url,
                            contentDescription = movie.name,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color.Transparent, Color.Black.copy(0.8f))
                                    )
                                )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = movie.name.toString(),
                            modifier = Modifier.align(Alignment.BottomCenter).basicMarquee(
                                animationMode = MarqueeAnimationMode.Immediately, // hoặc  WhileFocused
                                repeatDelayMillis = 1800, //độ trễ trước vong lap tiep // theo
                                spacing = MarqueeSpacing(48.dp), // khoảng cách giữa 2 lần lặp
                                velocity = 30.dp // tốc độ: dp per second
                            ),
                            color = Color.White.copy(0.8f)
                        )


                  //  }




                }
            }

        }


    }

}


@Composable
fun Test() {

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

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(180.dp)
                .background(brush, shape = RoundedCornerShape(8.dp))
        )


}
@Composable
fun LoadingScreen(showDialog: Boolean) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = null,
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CubeTransitionIndicator()

                }
            }
        )
    }


}

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

@Composable
fun SearchMovieScreen(homeViewModel: HomeViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text("SearchMovieScreen")

    }

}


//
//
//
//
//
//
//
//
//   val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestEmail()  // Yêu cầu quyền truy cập email
//        .build()
//    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)
//
// logout funtion
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        val homeUIState by homeViewModel.homeUIState.collectAsState()
//
//
//        Text("Home")
//        Button(onClick = {
//
//            homeViewModel.logout(googleSignInClient)
//
//        }) {
//
//            Text("LOG OUT")
//        }
//        if (homeUIState.isSuccess) {
//            navController.navigate(Constants.FIRST_ROUTE) {
//                popUpTo(Constants.HOME_ROUTE) { inclusive = true }
//            }
//        }
//    }
