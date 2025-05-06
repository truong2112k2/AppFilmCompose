package com.example.appfilm.presentation.ui.register

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.register.componets.SuccessDialog
import com.example.appfilm.presentation.ui.register.componets.LoadingDialog
import com.example.appfilm.presentation.ui.register.viewmodel.RegisterViewModel


@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel = hiltViewModel() ){
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        val registerFields = registerViewModel.registerFields
        val registerState = registerViewModel.registerState
        val inputEmail = registerFields.inputEmail
        val inputPassword = registerFields.inputPassword
        val reInputPassword =registerFields.reInputPassword
        val isShowDialogSuccess = registerFields.isShowDialogSuccess

        Text("Create An Account")

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value =inputEmail,
            onValueChange = { registerViewModel.updateEmail(newEmail = it )},
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent

            )
        )

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = inputPassword,
            onValueChange = { registerViewModel.updatePassword(it) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent

            )

        )

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = reInputPassword ,
            onValueChange = { registerViewModel.updateReInputPassword(it) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent

            )
        )

        Spacer(Modifier.height(5.dp))

        if(registerFields.errorText.isNotEmpty()){
            Text(registerFields.errorText)
        }


        Spacer(Modifier.height(5.dp))


        Button(onClick = {
            registerViewModel.register(email = inputEmail, password = inputPassword) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(4.dp),

        ) {
            Text("Register", modifier = Modifier.padding(6.dp))
        }

        Spacer(Modifier.height(5.dp))

        LaunchedEffect(registerState) {

            if(registerState.success){
                registerViewModel.updateErrorText("")

                registerViewModel.toggleIsShowDialogSuccess()
                Log.d(Constants.STATUS_TAG,"Register Success")

            }else if(registerState.error?.isNotBlank() == true){

                Log.d(Constants.STATUS_TAG,"Register Failed ${registerState.error}")


            }

        }
        LoadingDialog(showDialog = registerState.isLoading)
        SuccessDialog(
            isShowDialogSuccess,
            onDismiss = {
                registerViewModel.resendEmail()
            },
            onConfirm = {registerViewModel.toggleIsShowDialogSuccess()},
            R.drawable.ic_success,
            "Register is successfully, please check your email and verify"
        )
    }

}

