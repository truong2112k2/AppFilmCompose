package com.example.appfilm.presentation.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.example.appfilm.presentation.ui.CustomConfirmDialog
import com.example.appfilm.presentation.ui.CustomRetryBox
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.detail.components.CustomTextWithIcon
import com.example.appfilm.presentation.ui.detail.components.EpisodeMovieItem
import com.example.appfilm.presentation.ui.detail.components.ShowTrailerMovie
import com.example.appfilm.presentation.ui.detail.components.extractYoutubeVideoId
import com.example.appfilm.presentation.ui.detail.viewmodel.DetailEvent
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components.CustomButtonWithIcon
import com.example.appfilm.presentation.ui.isNetworkAvailable
import java.net.URLEncoder

@SuppressLint("ContextCastToActivity")
@Composable
fun DetailMovieScreen(
    navController: NavController,
    movieSlug: String,
    getDetailUIState: UIState,
    addFavouriteState: UIState,
    deleteFavouriteState: UIState,
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

            CustomRetryBox(
                onClick = {
                    onEvent(DetailEvent.ReTry(movieSlug))
                    isNetworkConnected = isNetworkAvailable(context)

                })


        } else {
            if (getDetailUIState.isLoading) {

                DetailMovieShimmer()

            } else {
                onEvent(DetailEvent.CheckFavouriteMovie(detailMovie._id.toString()))
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


                        var isDialogConfirmDelete by rememberSaveable { mutableStateOf(false) }
                        var isDialogConfirmAdd by rememberSaveable { mutableStateOf(false) }
                        var titleDialogConfirm by rememberSaveable { mutableStateOf("") }

                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.BottomStart),
                        ) {
                            if (isFavourite) {
                                if (deleteFavouriteState.isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (deleteFavouriteState.isSuccess) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier.clickable {
                                            isDialogConfirmDelete = true
                                            titleDialogConfirm = "Bỏ yêu thích ${detailMovie.name}?"
                                        }
                                    )
                                }
//
                            } else {
                                if (addFavouriteState.isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                } else {
                                    Icon(
                                        imageVector = if (addFavouriteState.isSuccess) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier.clickable {
                                            isDialogConfirmAdd = true
                                            titleDialogConfirm = "Yêu thích ${detailMovie.name}?"
                                        }
                                    )
                                }
                            }

                            CustomConfirmDialog(
                                title = titleDialogConfirm,
                                message = "",
                                onDismiss = {
                                    isDialogConfirmDelete = false
                                },
                                onConfirm = {
                                    onEvent(DetailEvent.DeleteFavouriteMovie(detailMovie._id.toString()))

                                    isDialogConfirmDelete = false
                                },
                                showDialog = isDialogConfirmDelete
                            )

                            CustomConfirmDialog(
                                title = titleDialogConfirm,
                                message = "",
                                onDismiss = { isDialogConfirmAdd = false },
                                onConfirm = {
                                    onEvent(DetailEvent.AddFavouriteMovie(detailMovie))

                                    isDialogConfirmAdd = false
                                },
                                showDialog = isDialogConfirmAdd
                            )
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
                                        Log.d("isShowTrailer", isShowTrailer.toString())
                                    },
                                    Icons.Default.PlayArrow,
                                    "Play Trailer"
                                )
                            }

                            ShowTrailerMovie(
                                isShowTrailer = isShowTrailer,
                                videoId,
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

                            CustomTextWithIcon(
                                it,
                                painterResource(R.drawable.ic_content),
                                fontSize = 14,
                                iconSize = 20
                            )


                        }

                        Spacer(modifier = Modifier.height(8.dp))


                        detailMovie.actor?.let {
                            if (it.isNotEmpty()) {
                                CustomTextWithIcon(
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
                                CustomTextWithIcon(
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
                                CustomTextWithIcon(
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

                                CustomTextWithIcon(
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
                                        EpisodeMovieItem(episode, detailMovie, onClick = {
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


