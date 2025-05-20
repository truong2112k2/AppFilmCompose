package com.example.appfilm.presentation.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun DetailMovieShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101018))
            .padding(16.dp)
    ) {
        // Ảnh cover
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                    color = Color.DarkGray
                ),
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 4.dp,
            shadowElevation = 8.dp,
            color = Color.Transparent
        ) {}

        Spacer(modifier = Modifier.height(16.dp))

        // Tiêu đề
        repeat(2) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(30.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                        color = Color.DarkGray
                    ),
                shape = RoundedCornerShape(4.dp),
                tonalElevation = 2.dp,
                shadowElevation = 4.dp,
                color = Color.Transparent
            ) {}
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nội dung mô tả
        repeat(4) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                        color = Color.DarkGray
                    ),
                shape = RoundedCornerShape(4.dp),
                tonalElevation = 1.dp,
                shadowElevation = 2.dp,
                color = Color.Transparent
            ) {}
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Text: "Tập phim"

        Row(
            modifier = Modifier.fillMaxWidth().placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                color = Color.DarkGray
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("", color = Color.White)
            Divider(modifier = Modifier.weight(1f).height(1.dp))
        }


        Spacer(modifier = Modifier.height(8.dp))

        // Danh sách tập phim (shimmer)
        LazyRow {
            items(5) {
                Surface(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(100.dp)
                        .height(150.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White),
                            color = Color.DarkGray
                        ),
                    shape = RoundedCornerShape(8.dp),
                    tonalElevation = 3.dp,
                    shadowElevation = 6.dp,
                    color = Color.Transparent
                ) {}
            }
        }
    }
}
