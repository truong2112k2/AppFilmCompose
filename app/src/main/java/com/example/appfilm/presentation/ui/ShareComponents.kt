package com.example.appfilm.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lint.kotlin.metadata.Visibility
import com.example.appfilm.R

@Composable
fun LoadingDialog(
    showDialog: Boolean,
    ) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = null,
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading....")
                }
            }
        )
    }
}

@Composable
fun CreateButton(
    onClick :() -> Unit,
    textButton: String
) {
    var scale by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
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
                        onClick()

                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(textButton, color = Color.White, modifier = Modifier.padding(8.dp))
    }
}

@Composable
fun CreateTitle(text: String) {
    Text(
        text,
        color = Color.White,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    )
}


@Composable
fun CreateButtonWithIcon(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.White),RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)

        ,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, color = Color.White,
                modifier = Modifier.padding(5.dp))
        }
    }

}



@Composable
fun CreateTextField(
    text: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(label) },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Black,
            focusedIndicatorColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White.copy(alpha = 0.8f)
        ),
        singleLine = true,
        visualTransformation = when {
            isPassword && !showPassword -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions = if (isPassword)
            KeyboardOptions(keyboardType = KeyboardType.Password)
        else
            KeyboardOptions.Default,
        trailingIcon = {

            if (isPassword) {
                val image = if (showPassword) R.drawable.ic_show_pass else R.drawable.ic_hide_password
                val description = if (showPassword) "Hide password" else "Show password"

                Box(
                    modifier = Modifier
                        //    .background(backgroundColor, shape = CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(
                                color = Color.Black, // 👈 đổi màu ripple tại đây,
                            )
                        ) {
                            showPassword = !showPassword
                        }
                        .padding(6.dp)
                ) {
                    Icon(
                        painter = painterResource(image),
                        contentDescription = description,
                        tint = Color.Gray
                    )
                }
            }
        }
    )
}



@Composable
fun CreateTextError(text: String) {
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
            text = text,
            color = Color.Red,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 15.sp
            )
        )

    }
}

@Composable
fun CreateBoxHideUI(){
    Box(
        modifier =Modifier.fillMaxSize().background(Color.Transparent).pointerInput(Unit){}
    ){

    }
}