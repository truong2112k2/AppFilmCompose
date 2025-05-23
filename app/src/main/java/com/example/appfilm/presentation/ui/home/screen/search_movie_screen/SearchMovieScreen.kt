package com.example.appfilm.presentation.ui.home.screen.search_movie_screen

import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel.SearchEvent
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel.SearchUiState
import com.example.appfilm.presentation.ui.register.componets.CustomTextError
import com.example.appfilm.presentation.ui.shimmerBrush

@Composable
fun SearchMovieScreen(
    navController: NavController,
    searchMoviesState: SearchUiState,
    listMovie: List<Movie>,
    listMovieSearch: List<MovieBySearch>,
    onEventClick: (SearchEvent) -> Unit

) {


    LaunchedEffect(Unit) {
        onEventClick(SearchEvent.GetMovies)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        CustomRandomBackground()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {


            var keyword by rememberSaveable { mutableStateOf("") }
            SearchBarRow(
                keyword,
                searchMoviesState,
                onKeywordChange = {
                    keyword = it
                },
                onSearchClick = {
                    onEventClick(SearchEvent.GetMoviesSearch(keyword))
                },
                onResetUiState = {
                    onEventClick(SearchEvent.ResetSearchState)

                }
            )
            if(searchMoviesState.error?.isNotEmpty() == true){
                CustomTextError(searchMoviesState.error)
            }

            Spacer(Modifier.height(8.dp))

            if (searchMoviesState.isSuccess) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    items(
                        listMovieSearch
                    ) {

                        MovieItemSearch(it, onClick = {
                            navController.navigate(
                                "DETAIL_ROUTE/${it.slug}"
                            )
                        })

                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    items(
                        listMovie
                    ) {

                        MovieItem(it, onClick = {
                            navController.navigate(
                                "DETAIL_ROUTE/${it.slug}"
                            )
                        })

                    }
                }
            }


        }


    }


}

@Composable
fun MovieItemSearch(
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
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data("https://phimimg.com/" + movie.poster_url)
//                .crossfade(true)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .memoryCachePolicy(CachePolicy.ENABLED)
//                .build(),
//            contentDescription = movie.name,
//            modifier = Modifier
//                .size(120.dp)
//                .clip(RoundedCornerShape(8.dp)),
//            contentScale = ContentScale.Crop
//        )
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
     searchMoviesState: SearchUiState  ,
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
            onClick =  if (searchMoviesState.isSuccess) onResetUiState else  onSearchClick,
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
                Text(text = if(searchMoviesState.isSuccess) "Xóa" else  "Tìm", color = Color.White)
            }

        }
    }
}
