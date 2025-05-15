package com.example.appfilm.presentation.ui

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.category.CategoryScreen
import com.example.appfilm.presentation.ui.detail.DetailMovieScreen
import com.example.appfilm.presentation.ui.fisrt.FirstScreen
import com.example.appfilm.presentation.ui.home.HomeScreen
import com.example.appfilm.presentation.ui.login.LogInScreen
import com.example.appfilm.presentation.ui.register.RegisterScreen
import com.example.appfilm.presentation.ui.reset_password.ResetPasswordScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    context: Context,
    navController: NavHostController,

    ){

    AnimatedNavHost(navController = navController, startDestination = Constants.FIRST_ROUTE){
        composable(Constants.LOG_IN_ROUTE) {
            LogInScreen(navController)
        }

        composable(Constants.REGISTER_ROUTE) {
            RegisterScreen(navController)
        }
        composable(Constants.FIRST_ROUTE) {
            FirstScreen(navController)
        }

        composable(Constants.HOME_ROUTE) {
            HomeScreen(navController)
        }

        composable(Constants.RESET_PASSWORD_ROUTE) {
            ResetPasswordScreen(navController)
        }

        composable(
            Constants.CATEGORY_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            CategoryScreen(navController)
        }


        composable(
            route = "DETAIL_ROUTE/{MovieSlug}"
            ,
            arguments = listOf(
                navArgument("MovieSlug") { type = NavType.StringType }
            ),
            enterTransition = { fadeIn(animationSpec = tween(700)) },
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {backStackEntry ->
            val movieSlug = backStackEntry.arguments?.getString("MovieSlug") ?: "null"

            DetailMovieScreen(navController, movieSlug)
        }
    }
}