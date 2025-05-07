package com.example.appfilm.presentation.ui.fisrt

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Background
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CreateBoxHideUI
import com.example.appfilm.presentation.ui.CreateButton
import com.example.appfilm.presentation.ui.CreateButtonWithIcon
import com.example.appfilm.presentation.ui.CreateTitle
import com.example.appfilm.presentation.ui.login.LogInScreen

@Composable
fun FirstScreen(navController: NavController) {
    var isHideUi by rememberSaveable { mutableStateOf(false ) }

    // set click
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Hình nền
        Image(
            painter = painterResource(id = Background.background),
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
            CreateTitle("Let's you in")
            Spacer(modifier = Modifier.height(32.dp))

            CreateButtonWithIcon("Continue with Facebook", R.drawable.ic_facebook, onClick = {})
            Spacer(modifier = Modifier.height(16.dp))

            CreateButtonWithIcon("Continue with Google", R.drawable.ic_google, onClick = {})

            Spacer(modifier = Modifier.height(16.dp))

            Text("or", color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))




            CreateButton(
                onClick = {
                    isHideUi = true
                    Log.d(Constants.STATUS_TAG, "isHideUI button: $isHideUi")

                    navController.navigate(Constants.LOG_IN_ROUTE)
                          }
                ,
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



                    isHideUi = true
                    annotatedText.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                        .firstOrNull()?.let {
                            navController.navigate(Constants.REGISTER_ROUTE){
                                launchSingleTop = true

                            }
                        }

                    Log.d(Constants.STATUS_TAG, "isHideUI: $isHideUi")

                }
            )



        }


        if(isHideUi){
            CreateBoxHideUI()

        }
    }
}

