package com.example.appfilm.presentation.ui.home.screen.search_movie_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieBySearch
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.components.MovieItem
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.components.SearchMovieItem
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.components.SearchBarRow
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel.SearchEvent
import com.example.appfilm.presentation.ui.register.componets.CustomTextError

@Composable
fun SearchMovieScreen(
    navController: NavController,
    searchMoviesState: UIState,
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
            if (searchMoviesState.error?.isNotEmpty() == true) {
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

                        SearchMovieItem(it, onClick = {
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

