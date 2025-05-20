package com.example.appfilm.presentation.ui.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomBoxHideUI
import com.example.appfilm.presentation.ui.CustomButton
import com.example.appfilm.presentation.ui.CustomTextField
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.CustomResultDialog
import com.example.appfilm.presentation.ui.login.components.CustomForgotPasswordText
import com.example.appfilm.presentation.ui.login.viewmodel.LoginEvent
import com.example.appfilm.presentation.ui.login.viewmodel.LoginFields
import com.example.appfilm.presentation.ui.login.viewmodel.LoginUIState
import com.example.appfilm.presentation.ui.reset_password.viewmodel.RegisterEvent
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    navController: NavController,
    loginFields: LoginFields,
    logInState: LoginUIState,
    sendEmailState: LoginUIState,
    evenClick : (LoginEvent) -> Unit
) {
    //  val logInState by loginViewModel.logInUIState.collectAsState()
    //  val sendEmailState by loginViewModel.sendEmailUIState.collectAsState()
    var isHideUi by rememberSaveable { mutableStateOf(false) }

    val activity = LocalContext.current as? ComponentActivity

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


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomRandomBackground()




        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
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
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            isHideUi = true
                            navController.navigate(Constants.FIRST_ROUTE) {
                                popUpTo(Constants.FIRST_ROUTE) {

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
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                CustomTextTitle("Welcome back!")
                Text(
                    stringResource(R.string.log_in_title),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )


                Spacer(Modifier.height(8.dp))

                CustomTextField(
                    loginFields.inputEmail,
                    "Enter your email",
                    onValueChange = {
                        //  loginViewModel.updateEmail(it)
                        evenClick(LoginEvent.updateEmail(it))
                    })

                Spacer(Modifier.height(8.dp))

                CustomTextField(
                    loginFields.inputPassword,
                    "Enter your password",
                    onValueChange = {

                        //     loginViewModel.updatePassword(it)
                        evenClick(LoginEvent.updatePassword(it))

                    },
                    true
                )




                if (loginFields.errorTextLogin.isNotEmpty()) {

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
                            text = loginFields.errorTextLogin,
                            color = Color.Red,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 15.sp
                            )
                        )
                        if (loginFields.errorTextLogin == "Email not verified") {
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

                                                //   loginViewModel.resendEmail()
                                                evenClick(LoginEvent.resendEmail)

                                            }
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Resend Email Verify",
                                    color = Color.White,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                        }

                    }
                }

                Spacer(Modifier.height(8.dp))


                CustomButton(
                    onClick = {

                        // loginViewModel.login()
                        evenClick(LoginEvent.login)

                        Log.d(
                            Constants.STATUS_TAG,
                            "Acc ${FirebaseAuth.getInstance().currentUser?.email}"
                        )
                    },
                    "Log In"

                )



                CustomForgotPasswordText(
                    onClickHere = {
                        /*
                          navController.navigate(Constants.FIRST_ROUTE){
                           launchSingleTop
                           popUpTo(Constants.FIRST_ROUTE){

                               inclusive = true
                           }
                       }
                         */
                        navController.navigate(Constants.RESET_PASSWORD_ROUTE)

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )






                LaunchedEffect(logInState) {

                    if (logInState.isSuccess) {
                        navController.navigate(Constants.HOME_ROUTE) {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }

                        Log.d(Constants.STATUS_TAG, "Login Success")

                        //  loginViewModel.updateErrorTextLogin("")
                        evenClick(LoginEvent.updateErrorTextLogin(""))

                    } else if (logInState.error?.isNotBlank() == true) {

                        Log.d(Constants.STATUS_TAG, "Login Failed ${logInState.error}")


                    }

                }


                CustomLoadingDialog(logInState.isLoading)
                CustomLoadingDialog(sendEmailState.isLoading)


                CustomResultDialog(
                    showDialog = loginFields.isShowSendEmailDialog,
                    message = loginFields.errorTextSendEmail,
                    onConfirm = {
                        //  loginViewModel.updateIsShowEmailDialog(false)
                        // loginViewModel.updateErrorTextLogin("")

                        evenClick(LoginEvent.updateIsShowEmailDialog(false))
                        evenClick(LoginEvent.updateErrorTextLogin(""))

                    }
                )


            }


        }

        if (isHideUi) {
            CustomBoxHideUI()
        }

    }


}







