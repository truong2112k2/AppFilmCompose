package com.example.appfilm.presentation.ui.register.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomSuccessDialog(
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
                TextButton(onClick = { onConfirm() }) {
                    Text("Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }

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
                    if (isSendingEmail) {
                        CircularProgressIndicator()
                        return@AlertDialog
                    }
                    if (messageSendEmail.isNotEmpty()) {
                        Text(text = messageSendEmail, color = Color.Red)

                    }
                }
            }
        )
    }
}

@Composable
fun CustomTextError(text: String) {
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
                fontSize = 15.sp, lineHeight = 17.sp
            )
        )

    }
}