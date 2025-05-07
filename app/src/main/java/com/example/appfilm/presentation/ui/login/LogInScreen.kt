package com.example.appfilm.presentation.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Background
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CreateBoxHideUI
import com.example.appfilm.presentation.ui.CreateButton
import com.example.appfilm.presentation.ui.CreateTextField
import com.example.appfilm.presentation.ui.CreateTitle
import com.example.appfilm.presentation.ui.LoadingDialog
import com.example.appfilm.presentation.ui.login.viewmodel.LogInViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(navController: NavController, loginViewModel: LogInViewModel = hiltViewModel() ){
    val logInState = loginViewModel.logInUIState
    val sendEmailState = loginViewModel.sendEmailUIState
    var isHideUi by rememberSaveable { mutableStateOf(false ) }

 DisposableEffect(Unit) {
     onDispose {
         isHideUi = true
         Log.d(Constants.STATUS_TAG,"DisposableEffect In LogInScreen ")

     }
 }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = Background.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null, tint = Color.White,
                        modifier = Modifier.clickable {
                            Log.d("123213","Click")
                            isHideUi = true
                            navController.navigate(Constants.FIRST_ROUTE){
                                popUpTo(Constants.FIRST_ROUTE){

                                    inclusive = true

                                }
                                launchSingleTop = true

                            }
                        }
                    )

                },
                actions = {}
            )

            Column(
                modifier = Modifier.weight(1f).verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                CreateTitle("Welcome back!")
                Text( stringResource(R.string.log_in_title), color = Color.White, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(8.dp))


                Spacer(Modifier.height(8.dp))

                CreateTextField(
                    loginViewModel.logInFields.inputEmail,
                    "Enter your email",
                    onValueChange = {
                        loginViewModel.updateEmail(it)
                    })

                Spacer(Modifier.height(8.dp))

                CreateTextField(
                    loginViewModel.logInFields.inputPassword,
                    "Enter your password",
                    onValueChange = {

                        loginViewModel.updatePassword(it)
                    },
                    true
                    )




                if(loginViewModel.logInFields.errorTextLogin.isNotEmpty()){

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "",
                            Modifier.padding(end = 8.dp),
                            tint = Color.Red
                        )
                        Text(
                            text = loginViewModel.logInFields.errorTextLogin,
                            color = Color.Red,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 15.sp
                            )
                        )
                        if(loginViewModel.logInFields.errorTextLogin == "Email not verified"){
                            var scale by remember { mutableFloatStateOf(1f) }

                            Spacer(Modifier.weight(1f))

                            Box(
                                modifier = Modifier
                                    .height(48.dp)
                                    .graphicsLayer {
                                        scaleX = scale
                                        scaleY = scale
                                    }
                                    .background(Color.Red, shape = RoundedCornerShape(8.dp))
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onPress = {
                                                scale = 0.95f
                                                tryAwaitRelease()
                                                scale = 1f
                                                loginViewModel.resendEmail()

                                            }
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Resend Email Verify", color = Color.White, modifier = Modifier.padding(8.dp))
                            }

                        }

                    }
                }

                Spacer(Modifier.height(8.dp))


                CreateButton(
                    onClick = {

                        Log.d(Constants.STATUS_TAG,"${loginViewModel.logInFields.inputEmail} ${loginViewModel.logInFields.inputPassword}")
                        loginViewModel.login()
                    },
                    "Log In"

                )




                LaunchedEffect(logInState) {

                    if(logInState.isSuccess){


                        navController.navigate(Constants.HOME_ROUTE)
                        Log.d(Constants.STATUS_TAG,"Login Success")

                        loginViewModel.updateErrorTextLogin("")
                    }else if(logInState.error?.isNotBlank() == true){

                        Log.d(Constants.STATUS_TAG,"Login Failed ${logInState.error}")


                    }

                }


                LoadingDialog(logInState.isLoading)
                LoadingDialog(sendEmailState.isLoading)
                Log.d(Constants.STATUS_TAG,"Dialog ${loginViewModel.logInFields.isShowSendEmailDialog}")



                ShowResultDialog(
                    showDialog = loginViewModel.logInFields.isShowSendEmailDialog,
                    message = loginViewModel.logInFields.errorTextSendEmail,
                    onDismiss = {
                        loginViewModel.updateIsShowEmailDialog(false)
                    }
                )



            }






        }

        if(isHideUi){
             CreateBoxHideUI()
        }

    }




}

@Composable
fun ShowResultDialog(
    showDialog: Boolean,
    message: String,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Notification") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }
}




