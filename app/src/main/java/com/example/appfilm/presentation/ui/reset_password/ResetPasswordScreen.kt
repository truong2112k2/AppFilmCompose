package com.example.appfilm.presentation.ui.reset_password

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomButton
import com.example.appfilm.presentation.ui.CustomTextField
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.CustomResultDialog
import com.example.appfilm.presentation.ui.register.componets.CustomTextError
import com.example.appfilm.presentation.ui.reset_password.viewmodel.ResetPassViewModel

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navController: NavController, resetPassViewModel: ResetPassViewModel = hiltViewModel()) {


    var isHideUi by rememberSaveable { mutableStateOf(false) }
    val resetPassFields = resetPassViewModel.resetPassFields
    val resetPassUIState = resetPassViewModel.resetPassState

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


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
                                Log.d("123213", "Click")
                                isHideUi = true
                                navController.navigate(Constants.LOG_IN_ROUTE) {
                                    popUpTo(Constants.LOG_IN_ROUTE) {

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
                    CustomTextTitle("Reset your passwrod!")
                    Text(
                        stringResource(R.string.reset_password),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )


                    Spacer(Modifier.height(8.dp))

                    CustomTextField(
                        resetPassFields.email,
                        "Enter your email",
                        onValueChange = {
                            resetPassViewModel.updateEmail(it)
                        })

                    Spacer(Modifier.height(8.dp))

                    if(resetPassUIState.error?.isNotEmpty() == true){
                        CustomTextError(resetPassUIState.error)
                    }





                    CustomButton(
                        onClick = {

                            resetPassViewModel.resetPassword()

                        },
                        "Reset password"

                    )

                    CustomLoadingDialog(resetPassUIState.isLoading)

                    CustomResultDialog(
                      resetPassFields.isShowDialogResult,
                        message = resetPassFields.resultString,
                        warningMessage = stringResource(R.string.warning_reset_pass),
                        onConfirm = {
                            resetPassViewModel.updateIsShowDialogResult(false)

                            isHideUi = true
                            navController.navigate(Constants.LOG_IN_ROUTE) {
                                popUpTo(Constants.LOG_IN_ROUTE) {

                                    inclusive = true

                                }
                                launchSingleTop = true

                            }
                        }
                    )
                }
            }
        }
    }
}