package com.example.appfilm.presentation.ui

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.category.CategoryScreen
import com.example.appfilm.presentation.ui.category.viewmodel.CategoryViewModel
import com.example.appfilm.presentation.ui.detail.DetailMovieScreen
import com.example.appfilm.presentation.ui.detail.viewmodel.DetailViewModel
import com.example.appfilm.presentation.ui.fisrt.FirstScreen
import com.example.appfilm.presentation.ui.fisrt.viewmodel.FirstUiState
import com.example.appfilm.presentation.ui.fisrt.viewmodel.FirstViewModel
import com.example.appfilm.presentation.ui.home.HomeScreen
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel
import com.example.appfilm.presentation.ui.login.LogInScreen
import com.example.appfilm.presentation.ui.login.viewmodel.LoginViewModel
import com.example.appfilm.presentation.ui.play_movie.PlayMovieScreen
import com.example.appfilm.presentation.ui.register.RegisterScreen
import com.example.appfilm.presentation.ui.register.viewmodel.RegisterViewModel
import com.example.appfilm.presentation.ui.reset_password.ResetPasswordScreen
import com.example.appfilm.presentation.ui.reset_password.viewmodel.ResetPassViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import java.net.URLDecoder

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    context: Context,
    navController: NavHostController,

    ) {

    AnimatedNavHost(navController = navController, startDestination = Constants.FIRST_ROUTE) {
        composable(Constants.LOG_IN_ROUTE) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            val loginFields = loginViewModel.logInFields
            val logInState by loginViewModel.logInUIState.collectAsState()
            val sendEmailState by loginViewModel.sendEmailUIState.collectAsState()

            LogInScreen(navController,
                loginFields,
                logInState,
                sendEmailState,
                evenClick = { loginViewModel.handleEventLogin(it) })
        }

        composable(Constants.REGISTER_ROUTE) {

            val registerViewModel = hiltViewModel<RegisterViewModel>()
            val registerFields = registerViewModel.registerFields
            val registerStateUi = registerViewModel.registerState
            val sendEmailState = registerViewModel.sendEmailUIState


            RegisterScreen(navController,
                registerFields,
                registerStateUi,
                sendEmailState,
                evenClick = { registerViewModel.handleEvent(it) })

        }
        composable(Constants.FIRST_ROUTE) {

            val firstViewModel = hiltViewModel<FirstViewModel>()
            val checkLoginState: FirstUiState by firstViewModel.checkLoginState.collectAsState()
            val logInWithoutPassState: FirstUiState by firstViewModel.loginWithoutPassState.collectAsState()
            FirstScreen(navController,
                checkLoginState,
                logInWithoutPassState,
                onEventClick = { firstViewModel.handleEvent(it) })
        }

        composable(Constants.HOME_ROUTE) {

            val homeViewModel = hiltViewModel<HomeViewModel>()
            val logoutState by homeViewModel.logoutState.collectAsState()
            HomeScreen(navController, logoutState, onEvenClick = { homeViewModel.handleEvent(it) })
        }

        composable(Constants.RESET_PASSWORD_ROUTE) {
            val resetPassViewModel = hiltViewModel<ResetPassViewModel>()

            val resetPassFields = resetPassViewModel.resetPassFields
            val resetPassState = resetPassViewModel.resetPassState

            ResetPasswordScreen(navController,
                resetPassFields,
                resetPassState,
                eventClick = { resetPassViewModel.handleEvent(it) })
        }

        composable(Constants.CATEGORY_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }) {

            val categoryViewModel = hiltViewModel<CategoryViewModel>()
            val listCategory by categoryViewModel.categories.collectAsState()

            CategoryScreen(navController,
                listCategory,
                onEvenClick = { categoryViewModel.handleEvent(it) },
                getList = {

                    categoryViewModel.getMoviesByCategory(it)
                })
        }


        composable(route = Constants.DETAIL_ROUTE,
            arguments = listOf(navArgument("MovieSlug") { type = NavType.StringType }),
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) })
        { backStackEntry ->

            val movieSlug = backStackEntry.arguments?.getString("MovieSlug") ?: "null"

            val vm = hiltViewModel<DetailViewModel>()
            val getDetailUIState by vm.getDetailMovieState.collectAsState()
            val detailMovie by vm.detailMovie.collectAsState()
            DetailMovieScreen(navController, movieSlug, getDetailUIState, detailMovie, onEvent = {
                vm.onEvent(it)
            })

        }

        composable(
            route = "PLAY_MOVIE_ROUTE/{MovieLink}",
            arguments = listOf(
                navArgument("MovieLink") { type = NavType.StringType }
            ),
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) { backStackEntry ->

            val encodedLink = backStackEntry.arguments?.getString("MovieLink") ?: ""
            val decodedLink = URLDecoder.decode(encodedLink, "UTF-8")

            PlayMovieScreen(decodedLink, context, navController)
        }

//        composable(route = Constants.PLAY_MOVIE_ROUTE,
//            arguments = listOf(
//                navArgument("MovieLink"){
//                    type = NavType.StringType
//                }
//            ),
//           // arguments = listOf(navArgument("MovieLink") { type = NavType.StringType }),
//            enterTransition = { fadeIn(animationSpec = tween(700)) },
//            exitTransition = { fadeOut(animationSpec = tween(700)) })
//        { backStackEntry ->
//
//            val movieLink = backStackEntry.arguments?.getString("MovieLink") ?: "null"
//
//            PlayMovieScreen(movieLink, context, navController)
//
//
//        }
    }
}