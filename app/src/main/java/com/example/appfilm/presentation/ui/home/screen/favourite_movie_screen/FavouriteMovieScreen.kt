package com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.presentation.ui.CustomLineProgressbar
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel.FavouriteEvent
import com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel.FavouriteUiState

@Composable
fun FavouriteMovieScreen(
    navController: NavController,
    getMoviesFavouriteState: FavouriteUiState,
    listMovies: List<Movie>,
    onEvent: (FavouriteEvent) -> Unit,
    onClickPickMovie: () -> Unit
) {

    LaunchedEffect(Unit) {
        onEvent(FavouriteEvent.GetFavouriteMovies)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CustomRandomBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()


        ) {


            if (getMoviesFavouriteState.isLoading) {
                CustomLineProgressbar(Color.White)
            } else {
                if (listMovies.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Your favourite list is empty",
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
                        Button(
                            onClick = {
                                onClickPickMovie()

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = "Pick movie now",
                                color = Color.Black
                            )
                        }
                    }


                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),

                        ) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)

                        ) {
                            items(listMovies) {
                                MovieItemFavourite(it, onClick = {
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


    }


}


@Composable
fun MovieItemFavourite(
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster_url)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = movie.name,
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                "${movie.origin_name.toString()}",
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
