package com.example.appfilm.presentation.ui.category

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.presentation.ui.CustomLineProgressbar
import com.example.appfilm.presentation.ui.category.viewmodel.CategoryEvent
import com.example.appfilm.presentation.ui.shimmerBrush
import kotlinx.coroutines.flow.Flow

@SuppressLint("AutoboxingStateCreation", "RememberReturnType")
@Composable
fun CategoryScreen(
    navController: NavController,
    listCategory: List<Category>,
    onEvenClick: (CategoryEvent) -> Unit,
    getList: (category: String) -> Flow<PagingData<MovieByCategory>>

) {
    LaunchedEffect(Unit) {
        onEvenClick(CategoryEvent.GetCategory)
    }

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }


    var view by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(listCategory) {
        if (listCategory.isNotEmpty() && selectedCategory == null) {
            selectedCategory = listCategory[0].slug
        }
    }


    val moviesPagingItems = remember(selectedCategory) {
        selectedCategory?.let {

             getList(it)

        }
    }?.collectAsLazyPagingItems()

    val loadState = moviesPagingItems?.loadState

    if (loadState != null) {
        when {
            loadState.refresh is LoadState.Loading -> {
               view = true
            }

            loadState.refresh is LoadState.Error -> {
                val e = (loadState.refresh as LoadState.Error).error
                // show error message UI với e.message
            }

            else -> {
                view = false


            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Color.Black, Color.White)))
            .statusBarsPadding()
    ) {


        if (listCategory.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 0.dp,
                containerColor = Color.Transparent,
                divider = {},
                indicator = {}
            ) {
                listCategory.forEachIndexed { index, category ->
                    val isSelected = selectedTabIndex == index
                    val backgroundColor = if (isSelected) Color.White else Color.Transparent
                    val textColor = if (isSelected) Color.Black else Color.White

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .background(backgroundColor)
                            .clickable {
                                selectedTabIndex = index
                                selectedCategory = category.slug
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = category.name, color = textColor)
                    }
                }
            }
            if(view){
                CustomLineProgressbar(Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))


            moviesPagingItems?.let { movies ->
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(movies.itemCount) { index ->
                        val movie = movies[index]
                        if (movie != null) {
                            MovieByCategoryItem(movie = movie,
                                onClick = {
                                    navController.navigate("DETAIL_ROUTE/${movie.slug}")
                                })
                        }
                    }

                    item {
                        if (movies.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            } ?: CustomLineProgressbar(Color.White)
        } else {
            CustomLineProgressbar(Color.White)
        }
    }

}




@Composable
fun MovieByCategoryItem(
    movie: MovieByCategory,
    onClick: () -> Unit ) {
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