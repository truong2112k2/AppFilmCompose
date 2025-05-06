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

/*
Đã login và xác nhận được, thiết kế UI đi
 */
@Composable
fun LogInScreen(navController: NavController, loginViewModel: LogInViewModel = hiltViewModel() ){
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {



        val logInFields = loginViewModel.logInFields
        val logInState = loginViewModel.logInUIState
        ///
        val inputEmail = logInFields.inputEmail
        val inputPassword = logInFields.inputPassword


        Text("Log In")

        Button(
            onClick = {
                navController.navigate(Constants.REGISTER_ROUTE)
            }
        ) {
            Text("Register Screen")
        }

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = inputEmail,
            onValueChange = { loginViewModel.updateEmail(it) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent

            )
        )

        Spacer(Modifier.height(5.dp))

        OutlinedTextField(
            value = inputPassword,
            onValueChange = { loginViewModel.updatePassword(it) },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent

            )

        )

        Spacer(Modifier.height(5.dp))


        Button(onClick = {

            loginViewModel.login(email = inputEmail, password = inputPassword)
             },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text("Log In", modifier = Modifier.padding(6.dp))
        }

        Button(onClick = {

            loginViewModel.resendEmail()
        },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            shape = RoundedCornerShape(4.dp),

            ) {
            Text("Send Email", modifier = Modifier.padding(6.dp))
        }



        LaunchedEffect(logInState) {

            if(logInState.isSuccess){

                Log.d(Constants.STATUS_TAG,"Login Success")

            }else if(logInState.error?.isNotBlank() == true){

                Log.d(Constants.STATUS_TAG,"Login Failed ${logInState.error}")


            }

        }




    }

}

@Composable
fun FirstScreen() {
    // set click
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Hình nền
        Image(
            painter = painterResource(id = R.drawable.background_test),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Lớp phủ màu đen mờ
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        )

        // Nội dung phía trên
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Let's you in", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(32.dp))

            SocialButton("Continue with Facebook", R.drawable.ic_facebook)
            SocialButton("Continue with Google", R.drawable.ic_google)

            Spacer(modifier = Modifier.height(16.dp))
            Text("or", color = Color.White)

            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SIGN IN WITH PASSWORD", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append("Sign Up")
                    }
                },
                color = Color.White
            )
        }
    }
}

@Composable
fun SocialButton(text: String, iconRes: Int) {
    OutlinedButton(
        onClick = { /* TODO */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Unspecified)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFilmTheme {

    }
}