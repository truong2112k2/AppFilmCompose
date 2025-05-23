package com.example.appfilm.presentation.ui.register

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomBoxHideUI
import com.example.appfilm.presentation.ui.CustomButton
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.CustomTextField
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.register.componets.CustomSuccessDialog
import com.example.appfilm.presentation.ui.register.componets.CustomTextError
import com.example.appfilm.presentation.ui.register.viewmodel.RegisterEvent
import com.example.appfilm.presentation.ui.register.viewmodel.RegisterFields


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    registerFields: RegisterFields,
    registerState: UIState,
    sendEmailState: UIState,
    evenClick: (RegisterEvent) -> Unit


) {


    val isShowDialogSuccess = registerFields.isShowDialogSuccess


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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            isHideUi = true
                            navController.navigate(Constants.FIRST_ROUTE) {
                                launchSingleTop
                                popUpTo(Constants.FIRST_ROUTE) {

                                    inclusive = true
                                }
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
                CustomTextTitle("Create An Account")
                Text(
                    stringResource(R.string.register_title),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(Modifier.height(5.dp))


                CustomTextField(
                    text = registerFields.inputEmail,
                    label = "Enter email",
                    onValueChange = {
                        //   registerViewModel.updateEmail(it)
                        evenClick(RegisterEvent.UpdateEmail(it))
                    }
                )

                Spacer(Modifier.height(5.dp))
                CustomTextField(
                    text = registerFields.inputPassword,
                    label = "Enter password",
                    onValueChange = {
                        //   registerViewModel.updatePassword(it)
                        evenClick(RegisterEvent.UpdatePassword(it))

                    },
                    true
                )

                Spacer(Modifier.height(5.dp))

                CustomTextField(
                    text = registerFields.reInputPassword,
                    label = "Re-enter password",
                    onValueChange = {
                        // registerViewModel.updateReInputPassword(it)
                        evenClick(RegisterEvent.UpdateReInputPassword(it))

                    },
                    true
                )


                Spacer(Modifier.height(5.dp))

                if (registerFields.errorTextRegister.isNotEmpty()) {
                    CustomTextError(registerFields.errorTextRegister)
                }


                Spacer(Modifier.height(5.dp))




                CustomButton(
                    onClick = {
                        //registerViewModel.register()
                        evenClick(RegisterEvent.Register)

                    },
                    "Register"
                )


                Spacer(Modifier.height(5.dp))

                LaunchedEffect(registerState) {

                    if (registerState.isSuccess) {
                        //   registerViewModel.updateErrorTextRegister("")

                        //   registerViewModel.toggleIsShowDialogSuccess()
                        evenClick(RegisterEvent.UpdateErrorTextRegister(""))
                        evenClick(RegisterEvent.ToggleIsShowDialogSuccess)

                        Log.d(Constants.STATUS_TAG, "Register Success")

                    } else if (registerState.error?.isNotBlank() == true) {

                        Log.d(Constants.STATUS_TAG, "Register Failed ${registerState.error}")


                    }

                }
                CustomLoadingDialog(showDialog = registerState.isLoading)

                CustomSuccessDialog(
                    isShowDialogSuccess,
                    onDismiss = {
                        // registerViewModel.resendEmail()
                        evenClick(RegisterEvent.ResendEmail)

                    },
                    onConfirm = {
                        //registerViewModel.toggleIsShowDialogSuccess()
                        // registerViewModel.reset(3)
                        evenClick(RegisterEvent.ToggleIsShowDialogSuccess)
                        evenClick(RegisterEvent.Reset(3))

                        isHideUi = true
                        navController.navigate(Constants.FIRST_ROUTE) {
                            launchSingleTop
                            popUpTo(Constants.FIRST_ROUTE) {

                                inclusive = true
                            }
                        }

                    },
                    R.drawable.ic_success,
                    "Register is successfully, please check your email and verify",
                    isSendingEmail = sendEmailState.isLoading,
                    registerFields.resultTextSendEmail
                )

            }


        }
        if (isHideUi) {
            CustomBoxHideUI()
        }


    }


}

