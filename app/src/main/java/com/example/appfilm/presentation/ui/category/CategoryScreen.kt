package com.example.appfilm.presentation.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

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
            .padding(16.dp)
    ) {
// Danh sách tên các tab

        // State để lưu tab đang được chọn
        var selectedTabIndex by remember { mutableStateOf(0) }

val listCategory by categoryViewModel.listCategory.collectAsState()
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.background(Color.Transparent)
            ) {
                listCategory.forEachIndexed { index, category ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(category.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nội dung tương ứng với tab được chọn
            when (selectedTabIndex) {
                0 -> Text("Nội dung trang chủ", modifier = Modifier.padding(16.dp))
                1 -> Text("Danh sách yêu thích", modifier = Modifier.padding(16.dp))
                2 -> Text("Cài đặt ứng dụng", modifier = Modifier.padding(16.dp))
            }


    }
}