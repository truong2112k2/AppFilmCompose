package com.example.appfilm.presentation.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.detail_movie.EpisodeMovie
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.example.appfilm.presentation.ui.CustomRetryBox
import com.example.appfilm.presentation.ui.detail.viewmodel.DetailEvent
import com.example.appfilm.presentation.ui.detail.viewmodel.DetailUiState
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.CustomButtonWithIcon
import com.example.appfilm.presentation.ui.isNetworkAvailable
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.net.URLEncoder

@SuppressLint("ContextCastToActivity")
@Composable
fun DetailMovieScreen(
    navController: NavController,
    movieSlug: String,
    getDetailUIState: DetailUiState,
    addFavouriteState: DetailUiState,
    isFavourite: Boolean,
    detailMovie: MovieDetail,


    onEvent: (DetailEvent) -> Unit

) {


    var isHideUi by rememberSaveable { mutableStateOf(false) }

    val activity = LocalContext.current as? ComponentActivity

    val context = LocalContext.current
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isHideUi = true
                Log.d(Constants.STATUS_TAG, "handleOnBackPressed")
                remove()
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    DisposableEffect(Unit) {
        activity?.onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    var isNetworkConnected by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {

        onEvent(DetailEvent.GetDetail(movieSlug))
        Log.d("kkkk", "id Movie ${detailMovie._id.toString()}")
      //  onEvent(DetailEvent.CheckFavouriteMovie(detailMovie._id.toString()  ))
        isNetworkConnected = isNetworkAvailable(context)
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Color.White)
                )
            )

    ) {


        if (!isNetworkConnected) {

            CustomRetryBox(onClick = {
                onEvent(DetailEvent.ReTry(movieSlug))
                isNetworkConnected = isNetworkAvailable(context)

            })


        } else {
            if (getDetailUIState.isLoading) {

                DetailMovieShimmer()

            } else {
                Log.d("kkkk", "id Movie  2 ${detailMovie._id.toString()}")
                onEvent(DetailEvent.CheckFavouriteMovie(detailMovie._id.toString()  ))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF101018)) // nền tối
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        // Poster
                        SubcomposeAsyncImage(
                            model = detailMovie.poster_url,
                            contentDescription = detailMovie.name,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            loading = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.DarkGray)
                                )
                            },
                            success = {
                                SubcomposeAsyncImageContent()
                            },
                            error = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Gray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }
                            }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color(0xFF101018)
                                        )
                                    )
                                )
                        )

//                        Box(
//                            modifier = Modifier
//                                .align(Alignment.TopStart)
//                                .padding(12.dp),
//                        ) {
//                            Icon(
//                                imageVector = if (addFavouriteState.isSuccess) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                                contentDescription = "",
//                                tint = Color.White,
//                                modifier = Modifier.clickable {
//                                    onEvent(DetailEvent.AddFavouriteMovie(detailMovie))
//                                }
//                            )
//
//                        }


                        detailMovie.vote_average?.let {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_star),
                                        contentDescription = "",
                                        tint = Color.Yellow,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))

                                    Text(
                                        text = "${detailMovie.vote_average}/10",
                                        style = TextStyle(
                                            fontSize = 13.sp,
                                            color = Color.White,
                                        )
                                    )
                                }

                            }
                        }

                        ///
                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.BottomStart),
                        ) {
                            if(isFavourite){
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "",
                                    tint = Color.White,
                                )
                            }else{
                                if(addFavouriteState.isLoading){
                                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(12.dp))
                                }else{
                                    Icon(
                                        imageVector = if (addFavouriteState.isSuccess) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier.clickable {
                                            onEvent(DetailEvent.AddFavouriteMovie(detailMovie))
                                        }
                                    )
                                }
                            }


                        }

                        var isShowTrailer by rememberSaveable { mutableStateOf(false) }


                        val trailerUrl = detailMovie.trailer_url
                        val videoId = trailerUrl?.let { extractYoutubeVideoId(it) }

                        if (!videoId.isNullOrEmpty()) {
                            Box(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .align(Alignment.BottomEnd),
                            ) {
                                CustomButtonWithIcon(
                                    onClick = {
                                        isShowTrailer = true
                                        Log.d("isShowTrailer", "$isShowTrailer")
                                    },
                                    Icons.Default.PlayArrow,
                                    "Play Trailer"
                                )
                            }

                            ShowTrailerMovie(
                                isShowTrailer = isShowTrailer,
                                videoId,
                                context,
                                onDismiss = {
                                    isShowTrailer = false
                                }
                            )
                        }
                    }


                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState)
                            .padding(16.dp)
                    ) {

                        Text(
                            detailMovie.name.toString(),
                            style = TextStyle(
                                fontFamily = Constants.FontTagessChrift,
                                fontSize = 35.sp,
                                color = Color.White
                            )
                        )
//                            Spacer(modifier = Modifier.weight(1f))
//                            Icon(
//                                imageVector = if(addFavouriteState.isSuccess) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                                contentDescription = "",
//                                tint = Color.White,
//                                modifier = Modifier.clickable {
//                                    onEvent(DetailEvent.AddFavouriteMovie(detailMovie))
//                                }
//                            )


                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "(${detailMovie.origin_name} - ${detailMovie.year})",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    color = Color.White,
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )


                        }



                        Spacer(modifier = Modifier.height(8.dp))

                        detailMovie.content?.let {

                            CreateTextWithIcon(
                                it,
                                painterResource(R.drawable.ic_content),
                                fontSize = 14,
                                iconSize = 20
                            )


                        }

                        Spacer(modifier = Modifier.height(8.dp))


                        detailMovie.actor?.let {
                            if (it.isNotEmpty()) {
                                CreateTextWithIcon(
                                    it.joinToString(),
                                    painterResource(R.drawable.ic_actor),
                                    fontSize = 14,
                                    iconSize = 20
                                )

                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        detailMovie.category?.let {
                            if (it.isNotEmpty()) {
                                CreateTextWithIcon(
                                    it.joinToString(),
                                    painterResource(R.drawable.ic_category),
                                    fontSize = 14,
                                    iconSize = 20
                                )

                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        detailMovie.country?.let {
                            if (it.isNotEmpty()) {
                                CreateTextWithIcon(
                                    text = it.joinToString(),
                                    painterResource(R.drawable.ic_language),
                                    fontSize = 14,
                                    iconSize = 20
                                )

                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        detailMovie.listEpisodeMovie?.let { episodes ->
                            if (episodes.isNotEmpty()) {

                                CreateTextWithIcon(
                                    text = "Danh sách tập phim",
                                    painterResource(R.drawable.ic_episode),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22,
                                    iconSize = 22
                                )


                                Spacer(modifier = Modifier.height(8.dp))

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 500.dp), // Giới hạn chiều cao
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    contentPadding = PaddingValues(4.dp)
                                ) {
                                    items(episodes.size) { index ->
                                        val episode = episodes[index]
                                        MovieCardVertical(episode, detailMovie, context, onClick = {
                                            val encodedUrl =
                                                URLEncoder.encode(episode.link_m3u8, "UTF-8")

                                            navController.navigate("PLAY_MOVIE_ROUTE/$encodedUrl")

                                        })
                                    }
                                }
                            }
                        }


                    }


                }


            }


        }


    }
}


@Composable
fun MovieCardVertical(
    episodeMovie: EpisodeMovie,
    detailMovie: MovieDetail,
    context: Context,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color.White, shape = CircleShape)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically

    ) {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(detailMovie.poster_url)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = detailMovie.name,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )


        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = episodeMovie.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


    }

}


@Composable
fun CreateTextWithIcon(
    text: String,
    painterResource: Painter,
    fontWeight: FontWeight? = null,
    fontSize: Int,
    iconSize: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier.size(iconSize.dp)
        )
        Spacer(Modifier.width(8.dp))
        Divider(
            color = Color.White,
            modifier = Modifier
                .height(50.dp)
                .width(1.dp)
        )
        Spacer(Modifier.width(8.dp))

        Text(
            text = "${text}",
            style = TextStyle(
                fontSize = fontSize.sp,
                color = Color.White,
                fontWeight = fontWeight
            ),
            textAlign = TextAlign.Start
        )
    }
}


@Composable
fun ShowTrailerMovie(
    isShowTrailer: Boolean,
    videoId: String,
    context: Context,
    onDismiss: () -> Unit
) {
    if (isShowTrailer) {
        Dialog(onDismissRequest = { onDismiss() }) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f),
                exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f)
            ) {
                Column(
                    modifier = Modifier
                        .width(700.dp)
                        .height(500.dp)
                        .background(Color.Black, shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LiveTvScreen(
                        videoId = videoId,
                        context = context,
                        width = 1540,
                        height = 1060
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { onDismiss() }
                    )
                }
            }
        }
    }
}


@Composable
fun LiveTvScreen(videoId: String, context: Context, width: Int, height: Int) {
    AndroidView(
        factory = {
            YouTubePlayerView(it).apply {
                layoutParams = ViewGroup.LayoutParams(width, height) // set kích thước native view
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }
        }
    )
}


fun extractYoutubeVideoId(url: String): String? {
    return Regex("v=([a-zA-Z0-9_-]{11})")
        .find(url)
        ?.groupValues
        ?.get(1)
}
