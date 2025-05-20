package com.example.appfilm.presentation.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.appfilm.presentation.ui.CustomLineProgressbar
import com.example.appfilm.presentation.ui.isNetworkAvailable

@SuppressLint("ContextCastToActivity")
@Composable
fun DetailMovieScreen(
    navController: NavController,
    movieSlug: String,
    getDetailUIState: DetailUiState,
    detailMovie: MovieDetail,

    onEvent: (DetailAction) -> Unit

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

        //onEvent(DetailAction.GetDetail(movieSlug))
          onEvent(DetailAction.GetDetail("ngoi-truong-xac-song"))
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
            .statusBarsPadding()
    ) {


        if (!isNetworkConnected) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "No internet, Please check your network",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 18.sp, color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 20.sp
                    ),
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )

                Button(onClick = {
                    //  onEvent(DetailAction.GetDetail(movieSlug))
                    onEvent(DetailAction.GetDetail("ngoi-truong-xac-song"))

                }) {
                    Text("Re Try")
                }
            }
        } else {
            if (getDetailUIState.isLoading) {
                CustomLineProgressbar(Color.White)
            } else {
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
                    }


                    //// Nội dung chính
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // giữ nguyên tỉ lệ so với ảnh bên trên
                            .verticalScroll(scrollState) // cho phép cuộn
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

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "(${detailMovie.origin_name} - ${detailMovie.year})",
                                style = TextStyle(
                                    fontSize = 13.sp,
                                    color = Color.White,
                                )
                            )

                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(R.drawable.ic_star),
                                contentDescription = "",
                                tint = Color.Yellow,
                                modifier = Modifier.size(12.dp)
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

                        Spacer(modifier = Modifier.height(12.dp))

                        detailMovie.actor?.let {
                            if (it.isNotEmpty()) {
                                CreateTextWithIcon(it.joinToString(),painterResource( R.drawable.ic_actor))
//                                Text(
//                                    text = "Diễn viên: ${it.joinToString()}",
//                                    style = TextStyle(
//                                        fontSize = 14.sp,
//                                        color = Color.White,
//                                    )
//                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        detailMovie.content?.let {
                            if (it.isNotBlank()) {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color.White,
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        detailMovie.category?.let {
                            if (it.isNotEmpty()) {
                                CreateTextWithIcon(it.joinToString(),painterResource( R.drawable.ic_category))

//                                Text(
//                                    text = "${it.joinToString()}",
//                                    style = TextStyle(
//                                        fontSize = 14.sp,
//                                        color = Color.White,
//                                    )
//                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))


                        CreateTextWithIcon(text = detailMovie.episode_current+"/"+detailMovie.episode_total, painterResource(R.drawable.ic_episode), fontWeight = FontWeight.Bold  )
                       detailMovie.country?.let {
                           if(it.isNotEmpty()){
                               CreateTextWithIcon(text = it.joinToString(), painterResource(R.drawable.ic_language)  )

                           }
                       }
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                painter = painterResource(R.drawable.ic_episode),
//                                contentDescription = "",
//                                tint = Color.White
//                            )
//                            Spacer(Modifier.width(4.dp))
//                            Text(
//                                text = "${detailMovie.episode_current+"/"+detailMovie.episode_total} Tập ",
//                                style = TextStyle(
//                                    fontSize = 24.sp,
//                                    color = Color.White,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                        }

                        LazyRow {
                            detailMovie.listEpisodeMovie?.let {
                                items(it) { episode ->
                                    MovieCardVertical(episode, detailMovie)

                                }

                            }
                        }


                    }


                }
                detailMovie.type?.let {
                Box(
                    modifier = Modifier.padding(12.dp).align(Alignment.TopEnd),
                    ) {
                    Text( it,
                        modifier = Modifier
                            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                            .padding(12.dp),
                        style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    )
                }


                }
            }


        }


    }
}

@Composable
fun MovieCardVertical(episodeMovie: EpisodeMovie, detailMovie: MovieDetail) {
    Card(
        modifier = Modifier
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current


            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(detailMovie.poster_url)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = detailMovie.name,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp) // hoặc weight nếu cần
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )



            Text(
                text = episodeMovie.name,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}


@Composable
fun CreateTextWithIcon(text: String, painterResource: Painter, fontWeight: FontWeight? = null ){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource,
            contentDescription = "",
            tint = Color.White
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "${text} Tập ",
            style = TextStyle(
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = fontWeight
            )
        )
    }
}