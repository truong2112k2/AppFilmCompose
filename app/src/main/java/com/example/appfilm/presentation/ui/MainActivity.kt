package com.example.appfilm.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.rememberNavController
import com.example.appfilm.presentation.ui.theme.AppFilmTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AppFilmTheme {
                val navController = rememberNavController()

                val context = LocalContext.current

                val videoUrl = "https://s4.phim1280.tv/20250325/xqyp5Z1I/index.m3u8"
//                Column(modifier = Modifier.fillMaxSize()) {
//                    VideoPlayer(url = videoUrl)
//                }

              //  PlayMovie(videoUrl, context, navController)

//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    YouTubePlayerWithFullscreen("IN5TD4VRcSM", context)
//
//                }
//                val vm = hiltViewModel<DetailViewModel>()
//                val getDetailUIState by vm.getDetailMovieState.collectAsState()
//                val detailMovie by vm.detailMovie.collectAsState()
//                DetailMovieScreen(navController, "", getDetailUIState, detailMovie, onEvent = {
//                    vm.onEvent(it)
//                })
                 Navigation(context, navController)


            }
        }
    }
}


@Composable
fun VideoPlayer(url: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true // hiện thanh điều khiển
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f) // hoặc dùng Modifier.height(200.dp)
    )
}







@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFilmTheme {

    }
}