package com.example.appfilm.presentation.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.fisrt.FirstScreen
import com.example.appfilm.presentation.ui.home.HomeScreen
import com.example.appfilm.presentation.ui.login.LogInScreen
import com.example.appfilm.presentation.ui.register.RegisterScreen

@Composable
fun Navigation(
    context: Context,
    navController: NavHostController,

    ){

    NavHost(navController = navController, startDestination = Constants.FIRST_ROUTE){
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
    }
}