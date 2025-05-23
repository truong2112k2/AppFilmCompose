package com.example.appfilm.presentation.ui.login.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


@Composable
fun CustomForgotPasswordText(
    modifier: Modifier = Modifier,
    onClickHere: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.White,

                )
        ) {
            append("If you forget your password, ")
        }

        pushStringAnnotation(tag = "CLICK_HERE", annotation = "click_here")
        withStyle(
            style = SpanStyle(
                color = Color.Red,
                textDecoration = TextDecoration.Underline,
                fontSize = 18.sp
            )
        ) {
            append("click here")
        }
        pop()
    }


    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart
    ) {
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "CLICK_HERE",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onClickHere()
                }
            }
        )
    }
}

