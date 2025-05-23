package com.example.appfilm.presentation.ui.category.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.presentation.ui.shimmerBrush

@Composable
fun CategoryMovieItem(
    movie: MovieByCategory,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(180.dp)
            .padding(8.dp)
            .clickable {
                onClick()
            }
            .shadow(8.dp, RoundedCornerShape(12.dp))
    ) {
        var isImageLoaded by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(
            model = "https://phimimg.com/" + movie.poster_url,
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
                        .background(shimmerBrush())
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