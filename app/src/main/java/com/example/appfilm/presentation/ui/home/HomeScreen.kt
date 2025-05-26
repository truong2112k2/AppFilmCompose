package com.example.appfilm.presentation.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomConfirmDialog
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.home.NavigationDrawerItem.Favorite.drawerScreenSaver
import com.example.appfilm.presentation.ui.home.components.CustomDrawerContent
import com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.FavouriteMovieScreen
import com.example.appfilm.presentation.ui.home.screen.favourite_movie_screen.viewmodel.FavouriteViewModel
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.HomeMovieScreen
import com.example.appfilm.presentation.ui.home.screen.home_movie_screen.viewmodel.HomeMovieViewModel
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.SearchMovieScreen
import com.example.appfilm.presentation.ui.home.screen.search_movie_screen.viewmodel.SearchMovieViewModel
import com.example.appfilm.presentation.ui.home.viewmodel.HomeEvent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(
    navController: NavController,
    logoutState: UIState,
    onEvenClick: (HomeEvent) -> Unit

) {

    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    @SuppressLint("StaticFieldLeak")
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

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


    val homeMovieViewModel = hiltViewModel<HomeMovieViewModel>()
    val getNewMovieState by homeMovieViewModel.getNewMovieState.collectAsState()
    val movies by homeMovieViewModel.movies.collectAsState()


    val favouriteViewModel = hiltViewModel<FavouriteViewModel>()
    val getFavouriteMovieUiState by favouriteViewModel.getMoviesFavouriteState.collectAsState()
    val listFavouriteMovies by favouriteViewModel.listMovies.collectAsState()


    val searchMovieViewModel = hiltViewModel<SearchMovieViewModel>()
    val searchMovieState by searchMovieViewModel.searchMoviesState.collectAsState()
    val listMovieSearch by searchMovieViewModel.listMovieSearch.collectAsState()
    val listMovie by searchMovieViewModel.listMovie.collectAsState()


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
                title = {

                    Text(selectedScreen.title)

                },
                navigationIcon = {

                    IconButton(onClick = {
                        coroutineScope.launch { drawerState.open() }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }


                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        isShowDiaLogConfirm = true
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }

                }
            )



            Box {
                when (selectedScreen) {
                    is NavigationDrawerItem.Home -> HomeMovieScreen(
                        navController = navController,
                        context = context,
                        getNewMovieState = getNewMovieState,
                        movies = movies,
                        onEventClick = {

                            homeMovieViewModel.handleEvent(it)
                        },

                    )


                    is NavigationDrawerItem.Favorite -> FavouriteMovieScreen(
                        navController,
                        getFavouriteMovieUiState,
                        listFavouriteMovies,
                        onEvent = {
                            favouriteViewModel.handleEvent(it)
                        },
                        onClickPickMovie = {
                            selectedScreen = NavigationDrawerItem.Home

                        })

                    is NavigationDrawerItem.Search -> SearchMovieScreen(
                        navController,
                        searchMovieState,
                        listMovie,
                        listMovieSearch,
                        onEventClick = {
                            searchMovieViewModel.handleEvent(it)
                        }

                    )
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

            onEvenClick(HomeEvent.Logout(googleSignInClient))
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














