package com.example.appfilm.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.home.NavigationDrawerItem

@Composable
fun CustomDrawerContent(
    selectedScreen: NavigationDrawerItem,
    onItemSelected: (NavigationDrawerItem) -> Unit
) {
    val items = listOf(
        NavigationDrawerItem.Home,
        NavigationDrawerItem.Favorite,
        NavigationDrawerItem.Search
    )


    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .clip(RoundedCornerShape(16.dp))

    ) {
        CustomRandomBackground()
        Column(
            modifier = Modifier
                .background(Color.Transparent)

        ) {
            items.forEach { screen ->
                val isSelected = selectedScreen.title == screen.title
                val textColor = if (isSelected) Color.Black else Color.White

                Column {

                    Spacer(Modifier.statusBarsPadding())
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isSelected) {
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.White.copy(alpha = 0.5f), Color.White.copy(
                                                alpha = 0.3f
                                            )
                                        )
                                    )
                                } else {
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Transparent
                                        )
                                    )
                                }


                            )
                            .clickable {
                                onItemSelected(screen)
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(screen.icon, contentDescription = screen.title, tint = textColor)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = screen.title, color = textColor)

                        Spacer(modifier = Modifier.weight(1f))

                        // Thêm vạch bên phải (với chiều cao bằng chiều cao của Row)
                        Box(
                            modifier = Modifier
                                .height(24.dp)  // Điều chỉnh chiều cao vạch cho vừa với Row
                                .width(2.dp)  // Độ dày của vạch
                                .background(
                                    if (isSelected) {
                                        Color.Black
                                    } else {
                                        Color.Transparent

                                    }
                                )
                        )

                    }

                }
            }
        }
    }


}

