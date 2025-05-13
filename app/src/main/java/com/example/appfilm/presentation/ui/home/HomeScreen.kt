package com.example.appfilm.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.presentation.ui.home.components.CustomModalNavigationDrawer
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreen(
    navController: NavController,

    homeViewModel: HomeViewModel = hiltViewModel()

) {

    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    @SuppressLint("StaticFieldLeak")
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    CustomModalNavigationDrawer(navController, context, homeViewModel)


}














