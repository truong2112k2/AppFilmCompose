package com.example.appfilm.presentation.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenShimmer() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)


    ) {
        Box(
            modifier = Modifier
                .size(550.dp) // Kích thước Box
                .border(
                    width = 1.dp, // Độ dày viền
                    color = Color.White, // Màu viền
                    shape = RoundedCornerShape(16.dp) // Bo góc
                )
                .clip(RoundedCornerShape(16.dp))
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                    color = Color.DarkGray
                )
        )
        Spacer(Modifier.height(4.dp))

        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                    color = Color.DarkGray
                )
        )
        Spacer(Modifier.height(4.dp))

        LazyRow() {
            items(20) {
                Text(
                    "", modifier =
                    Modifier
                        .height(150.dp)
                        .width(100.dp)
                        .border(
                            width = 1.dp, // Độ dày viền
                            color = Color.White, // Màu viền
                            shape = RoundedCornerShape(16.dp) // Bo góc
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                            color = Color.DarkGray
                        )
                )
            }
        }

    }
}