package com.example.appfilm.presentation.ui.home.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomBoxHideUI
import com.example.appfilm.presentation.ui.CustomConfirmDialog
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.home.NavigationDrawerItem
import com.example.appfilm.presentation.ui.home.NavigationDrawerItem.Favorite.drawerScreenSaver
import com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.FavouriteMovieScreen
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.HomeMovieScreen
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.SearchMovieScreen
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CustomModalNavigationDrawer(
    navController: NavController,
    context: Context,
    homeViewModel: HomeViewModel,
    googleSignInClient: GoogleSignInClient
) {

    var isHideUi by rememberSaveable { mutableStateOf(false) }

    val activity = context as? ComponentActivity

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isHideUi = true
                Log.d(Constants.STATUS_TAG, "handleOnBackPressed")
                remove()
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    DisposableEffect(Unit) {
        activity?.onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var selectedScreen by rememberSaveable(stateSaver = drawerScreenSaver) {
        mutableStateOf<NavigationDrawerItem>(NavigationDrawerItem.Home)
    }

    var isShowDiaLogConfirm by rememberSaveable { mutableStateOf(false) }

    val logoutState by homeViewModel.logoutState.collectAsState()


        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {


                CustomDrawerContent(
                    selectedScreen = selectedScreen,
                    onItemSelected = {
                        selectedScreen = it
                        coroutineScope.launch { drawerState.close() }
                    }
                )


            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Black, Color.White)
                        )
                    )
            ) {
                TopAppBar(
                    title = { Text(selectedScreen.title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,       // Nền đỏ
                        titleContentColor = Color.White,  // Màu chữ (tuỳ chọn)
                        navigationIconContentColor = Color.White // Màu icon (tuỳ chọn)
                    ),
                    actions = {
                        IconButton(onClick = {
                            isShowDiaLogConfirm = true
                        }) {
                            Icon(
                                Icons.Default.ExitToApp,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    }
                )

                Box(

                ) {
                    when (selectedScreen) {
                        is NavigationDrawerItem.Home -> HomeMovieScreen(navController, context)
                        is NavigationDrawerItem.Favorite -> FavouriteMovieScreen(homeViewModel)
                        is NavigationDrawerItem.Search -> SearchMovieScreen(homeViewModel)
                    }
                }

            }


        }






    CustomConfirmDialog(
        "Log out your account",
        "Are you sure to log out",
        showDialog = isShowDiaLogConfirm,
        onConfirm = {
            isShowDiaLogConfirm = false
            homeViewModel.logout(googleSignInClient)
        },
        onDismiss = {
            isShowDiaLogConfirm = false
        }
    )



    if (logoutState.isSuccess) {
        isHideUi = true
        navController.navigate(Constants.FIRST_ROUTE) {
            launchSingleTop
            popUpTo(Constants.HOME_ROUTE) {

                inclusive = true
            }
        }
    }

}

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

                Column(

                ) {

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

