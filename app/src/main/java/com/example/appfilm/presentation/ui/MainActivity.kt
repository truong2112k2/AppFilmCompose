package com.example.appfilm.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.login.viewmodel.LogInViewModel
import com.example.appfilm.presentation.ui.theme.AppFilmTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFilmTheme {
                val navController = rememberNavController()

                val context = LocalContext.current
                Navigation(context = context, navController = navController)
               // FirstScreen()

            }
        }
    }
}


//@Composable
//fun LogInScreen(navController: NavController, loginViewModel: LogInViewModel = hiltViewModel() ){
//    Column(
//        modifier = Modifier.fillMaxSize().padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//
//
//        val logInFields = loginViewModel.logInFields
//        val logInState = loginViewModel.logInUIState
//        val inputEmail = logInFields.inputEmail
//        val inputPassword = logInFields.inputPassword
//
//
//        Text("Log In")
//
//
//        Spacer(Modifier.height(5.dp))
//
//        OutlinedTextField(
//            value = inputEmail,
//            onValueChange = { loginViewModel.updateEmail(it) },
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            colors = TextFieldDefaults.colors(
//                unfocusedIndicatorColor = Color.Transparent
//
//            )
//        )
//
//        Spacer(Modifier.height(5.dp))
//
//        OutlinedTextField(
//            value = inputPassword,
//            onValueChange = { loginViewModel.updatePassword(it) },
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            colors = TextFieldDefaults.colors(
//                unfocusedIndicatorColor = Color.Transparent
//
//            )
//
//        )
//
//        Spacer(Modifier.height(5.dp))
//
//        if(logInFields.errorText.isNotEmpty()){
//            Text(logInFields.errorText)
//        }
//
//
//        Button(onClick = {
//
//            loginViewModel.login()
//             },
//            modifier = Modifier.fillMaxWidth().padding(8.dp),
//            shape = RoundedCornerShape(4.dp),
//
//            ) {
//            Text("Log In", modifier = Modifier.padding(6.dp))
//        }
//
//
//
//
//        LaunchedEffect(logInState) {
//
//            if(logInState.isSuccess){
//
//
//                navController.navigate(Constants.HOME_ROUTE)
//                Log.d(Constants.STATUS_TAG,"Login Success")
//
//                loginViewModel.updateErrorText("")
//            }else if(logInState.error?.isNotBlank() == true){
//
//                Log.d(Constants.STATUS_TAG,"Login Failed ${logInState.error}")
//
//
//            }
//
//        }
//
//            LoadingDialog(logInState.isLoading)
//
//
//
//
//
//    }
//
//}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFilmTheme {

    }
}