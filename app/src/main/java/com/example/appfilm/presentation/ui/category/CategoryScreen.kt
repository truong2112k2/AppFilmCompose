package com.example.appfilm.presentation.ui.category

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieByCategory

@SuppressLint("AutoboxingStateCreation")
@Composable
fun CategoryScreen(categoryViewModel: CategoryViewModel = hiltViewModel()){

    LaunchedEffect(Unit) {
        categoryViewModel.getCategory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Color.White)
                )
            )
    ) {

        var selectedTabIndex by rememberSaveable  { mutableStateOf(0) }


        var selectedCategory by rememberSaveable { mutableStateOf<String?>(null ) }

        val listCategory by categoryViewModel.categories.collectAsState()

        val moviesByCategory by categoryViewModel.moviesByCategory.collectAsState()

        val getMoviesByCategoryState by categoryViewModel.getMoviesByCategoryState.collectAsState()
        if (listCategory.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dp,
                 containerColor = Color.Transparent,
                divider = {}

            ) {
                listCategory.forEachIndexed { index, category ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                           categoryViewModel.getMoviesByCategory(category.slug)
                                  },
                        text = { Text(category.name) },
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if(getMoviesByCategoryState.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }else{
                LazyColumn {
                    items(moviesByCategory) {
                        MovieByCategoryItem(movie = it )
                    }

                }
            }


        } else {
            // Hiển thị loading hoặc thông báo không có dữ liệu
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }





    }
}

@Composable
fun MovieByCategoryItem(
    movie: MovieByCategory,

) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(180.dp)
            .padding(8.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
    ) {
        var isImageLoaded by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(
            model = movie.poster_url,
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