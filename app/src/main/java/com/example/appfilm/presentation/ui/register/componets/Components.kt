package com.example.appfilm.presentation.ui.register.componets

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
fun SuccessDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    imageRes: Int,
    message: String
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
                }
            }
        )
    }
}
