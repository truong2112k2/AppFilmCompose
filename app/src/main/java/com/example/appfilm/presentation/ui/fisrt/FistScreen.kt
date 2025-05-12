package com.example.appfilm.presentation.ui.fisrt

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomButton
import com.example.appfilm.presentation.ui.CustomButtonWithIcon
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.CustomResultDialog
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

/*
getString(R.string.default_web_client_id) -> get Id Token
 */


@Composable
fun FirstScreen(navController: NavController, firstViewModel: FirstViewModel = hiltViewModel()) {


    val checkLoginState  by firstViewModel.checkLoginState.collectAsState()
    CustomLoadingDialog(checkLoginState.isLoading)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LaunchedEffect(Unit) {
            firstViewModel.checkLogin(
                onLogin = {
                    navController.navigate(Constants.HOME_ROUTE) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )


        }






        val firstUiState by firstViewModel.loginWithoutPassState.collectAsState()
        val context = LocalContext.current
        val clientId = context.getString(R.string.default_web_client_id)

        val googleSignInClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build()
        )

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let {
                    firstViewModel.signInWithGoogle(it)
                }
            } catch (e: ApiException) {
                Log.e("LOGIN", "Google sign in failed", e)
            }
        }

        CustomRandomBackground()


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextTitle("Let's you in")
            Spacer(modifier = Modifier.height(32.dp))


            CustomButtonWithIcon("Continue with Google", R.drawable.ic_google, onClick = {

                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)

            })

            Spacer(modifier = Modifier.height(16.dp))

            Text("or", color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))




            CustomButton(
                onClick = {

                    navController.navigate(Constants.LOG_IN_ROUTE)
                },
                "SIGN IN WITH PASSWORD"
            )


            Spacer(modifier = Modifier.height(16.dp))

            val annotatedText = buildAnnotatedString {
                append("Don't have an account? ")

                // Gắn tag để bắt sự kiện nhấn
                pushStringAnnotation(tag = "SignUp", annotation = "SignUp")

                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 16.sp
                    )
                ) {
                    append("Sign Up")
                }

                pop()
            }

            ClickableText(
                text = annotatedText,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                onClick = { offset ->
                    annotatedText.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate(Constants.REGISTER_ROUTE) {
                                launchSingleTop = true

                            }
                        }


                }
            )


        }

        val resultMessage = firstViewModel.resultText
        CustomLoadingDialog(firstUiState.isLoading)

        CustomResultDialog(
            firstViewModel.isShowDialogResult,
            message = resultMessage,
            onConfirm = { firstViewModel.updateIsShowDialogResult(false) }
        )
        LaunchedEffect(firstUiState) {

            if (firstUiState.isSuccess) {
                navController.navigate(Constants.HOME_ROUTE) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

                Log.d(Constants.STATUS_TAG, "Login Success")


            } else if (firstUiState.error?.isNotBlank() == true) {

                Log.d(Constants.STATUS_TAG, "Login Failed ${firstUiState.error}")


            }

        }

    }
}

