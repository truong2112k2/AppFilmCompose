package com.example.appfilm.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.appfilm.presentation.ui.theme.AppFilmTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppFilmTheme {

            }
        }
    }
}

@Composable
fun Greeting() {



}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppFilmTheme {

    }
}