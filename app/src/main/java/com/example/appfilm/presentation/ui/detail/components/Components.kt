package com.example.appfilm.presentation.ui.detail.components

import android.content.Context
import android.view.ViewGroup
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.appfilm.domain.model.detail_movie.EpisodeMovie
import com.example.appfilm.domain.model.detail_movie.MovieDetail
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

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
