package com.example.appfilm.presentation.ui.register.componets

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp



@Composable
fun SuccessDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    imageRes: Int,
    message: String,
    isSendingEmail: Boolean,
    messageSendEmail: String,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {onConfirm()}) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss()}

                ) {
                    Text("Send New Email")
                }
            },
            title = null,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(text = message)
                    Spacer(modifier = Modifier.height(8.dp))
                    if(isSendingEmail){
                        Log.d("21312", isSendingEmail.toString())
                        CircularProgressIndicator()
                        return@AlertDialog
                    }
                    if(messageSendEmail.isNotEmpty()){
                        Text(text = messageSendEmail, color = Color.Red)

                    }
                }
            }
        )
    }
}
