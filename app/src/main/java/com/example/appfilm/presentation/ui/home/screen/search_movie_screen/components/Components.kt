package com.example.appfilm.presentation.ui.home.screen.search_movie_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.shimmerBrush


@Composable
fun MovieItem(
    movie: Movie,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        var isImageLoaded by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(
            model = movie.poster_url,
            contentDescription = movie.name,
            modifier = Modifier
                .size(120.dp)
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

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (!isImageLoaded) {
                // Shimmer for title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(4.dp)
                        .background(shimmerBrush(), shape = RoundedCornerShape(4.dp))
                )

                // Shimmer for subtitle
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(4.dp)
                        .background(shimmerBrush(), shape = RoundedCornerShape(4.dp))
                )
            } else {
                Text(
                    movie.name.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    movie.origin_name.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

    }

}

@Composable
fun SearchBarRow(
    keyword: String,
    searchMoviesState: UIState,
    onKeywordChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onResetUiState: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color.White, shape = RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))

        ) {
            OutlinedTextField(
                value = keyword,
                onValueChange = onKeywordChange,
                modifier = Modifier.height(54.dp),
                singleLine = true,
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                placeholder = {
                    Text(
                        text = "Nhập từ khóa",
                        color = Color.LightGray,
                        style = TextStyle(color = Color.White, fontSize = 18.sp)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.LightGray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.Black,


                    ),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") }
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
        OutlinedButton(
            onClick = if (searchMoviesState.isSuccess) onResetUiState else onSearchClick,
            modifier = Modifier
                .height(50.dp)
                .width(100.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            )
        ) {
            if (searchMoviesState.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(25.dp),
                    strokeWidth = 2.dp,
                    trackColor = Color.Red
                )
            } else {
                Text(text = if (searchMoviesState.isSuccess) "Xóa" else "Tìm", color = Color.White)
            }

        }
    }
}
@Composable
fun SearchMovieItem(
    movie: MovieBySearch,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        var isImageLoaded by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(
            model = "https://phimimg.com/" + movie.poster_url,
            contentDescription = movie.name,
            modifier = Modifier
                .size(120.dp)
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
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (!isImageLoaded) {
                // Shimmer for title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(4.dp)
                        .background(shimmerBrush(), shape = RoundedCornerShape(4.dp))
                )

                // Shimmer for subtitle
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .padding(4.dp)
                        .background(shimmerBrush(), shape = RoundedCornerShape(4.dp))
                )
            } else {
                Text(
                    movie.name.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    movie.origin_name.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }


    }

}
