package com.example.appfilm.presentation.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerScreen(val title: String, val icon: ImageVector) {
    object Home : DrawerScreen("Trang chính", Icons.Default.Home)
    object Favorite : DrawerScreen("Yêu thích", Icons.Default.Favorite)
    object Settings : DrawerScreen("Cài đặt", Icons.Default.Settings)

    companion object {
        fun fromTitle(title: String): DrawerScreen {
            return when (title) {
                Home.title -> Home
                Favorite.title -> Favorite
                Settings.title -> Settings
                else -> Home
            }
        }
    }
    val DrawerScreenSaver: Saver<DrawerScreen, String> = Saver(
        save = { it.title },
        restore = { DrawerScreen.fromTitle(it) }
    )

}
