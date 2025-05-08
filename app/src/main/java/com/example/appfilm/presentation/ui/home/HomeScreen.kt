package com.example.appfilm.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.appfilm.common.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()  // Yêu cầu quyền truy cập email
            .build()
        val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)

        Button(onClick = {

            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut().addOnSuccessListener {
                navController.navigate(Constants.FIRST_ROUTE) {
                    popUpTo(Constants.HOME_ROUTE) { inclusive = true }
                }
            }
        }) {

            Text("LOG OUT")
        }
    }

}