package com.example.appfilm.presentation.ui.fisrt

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomButton
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.CustomResultDialog
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.fisrt.viewmodel.FirstEvent
import com.example.appfilm.presentation.ui.isNetworkAvailable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


@Composable
fun FirstScreen(
    navController: NavController,
    checkLoginState: UIState,
    logInWithoutPassState: UIState,
    onEventClick: (FirstEvent) -> Unit


) {


    CustomLoadingDialog(logInWithoutPassState.isLoading)

    var showDialogError by rememberSaveable { mutableStateOf(false) }
    var errorText by rememberSaveable { mutableStateOf("") }


    val context = LocalContext.current
    val clientId = context.getString(R.string.default_web_client_id)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LaunchedEffect(Unit) {
            onEventClick(FirstEvent.CheckLogin)
        }


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

                    onEventClick(FirstEvent.SignInWithGoogle(it))

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



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.White), RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        if (isNetworkAvailable(context)) {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        } else {
                            errorText = "No internet"
                            showDialogError = true
                        }

                    }
                    .padding(16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Continue with Google", color = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
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





        LaunchedEffect(logInWithoutPassState) {

            if (logInWithoutPassState.isSuccess) {
                navController.navigate(Constants.HOME_ROUTE) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

                Log.d(Constants.STATUS_TAG, "Login Success")


            } else if (logInWithoutPassState.error?.isNotBlank() == true) {

                showDialogError = true
                errorText = logInWithoutPassState.error.toString()
                Log.d(Constants.STATUS_TAG, "Login Failed ${logInWithoutPassState.error}")


            }

        }
        CustomResultDialog(
            showDialogError,
            errorText,
            onConfirm = {
                showDialogError = false
            }
        )

    }

    CustomLoadingDialog(checkLoginState.isLoading)

    if (checkLoginState.isSuccess) {
        navController.navigate(Constants.HOME_ROUTE) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
    }

}

