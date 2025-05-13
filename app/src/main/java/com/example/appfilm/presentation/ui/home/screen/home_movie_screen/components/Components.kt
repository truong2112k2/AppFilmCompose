package com.example.appfilm.presentation.ui.home.screen.home_movie_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.appfilm.domain.model.Movie

@Composable
fun CustomButtonWithIcon(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String
) {

    Button(
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White // Nền trắng
        ),
        shape = RoundedCornerShape(12.dp), // Bo góc nhẹ
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp) // Đổ bóng nhẹ (tùy chọn)
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon, // Đổi icon nếu muốn
            contentDescription = "Icon",
            tint = Color.Black // Icon màu đen
        )
        Spacer(modifier = Modifier.width(8.dp)) // Khoảng cách giữa icon và text
        Text(
            text = text,
            color = Color.Black // Text màu đen
        )
    }


}


