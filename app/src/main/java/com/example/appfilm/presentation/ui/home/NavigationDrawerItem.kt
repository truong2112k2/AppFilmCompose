package com.example.appfilm.presentation.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDrawerItem(val title: String, val icon: ImageVector) {
    data object Home : NavigationDrawerItem(title = "Home", icon = Icons.Default.Home)
    data object Favorite : NavigationDrawerItem(title = "Favorite", icon = Icons.Default.Favorite)
    data object Search : NavigationDrawerItem(title = "Search", icon = Icons.Default.Search)

    companion object {
        fun fromTitle(title: String): NavigationDrawerItem {
            return when (title) {
                Home.title -> Home
                Favorite.title -> Favorite
                Search.title -> Search
                else -> Home
            }
        }
    }

    val drawerScreenSaver: Saver<NavigationDrawerItem, String> =
        Saver( // giúp saveable có thể lưu 1 object
            save = { it.title },
            restore = { fromTitle(it) }
        )

}
