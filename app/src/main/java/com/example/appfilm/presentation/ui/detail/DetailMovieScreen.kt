package com.example.appfilm.presentation.ui.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appfilm.R
import com.example.appfilm.common.Constants
import com.example.appfilm.presentation.ui.CustomBoxHideUI
import com.example.appfilm.presentation.ui.CustomButton
import com.example.appfilm.presentation.ui.CustomLoadingDialog
import com.example.appfilm.presentation.ui.CustomRandomBackground
import com.example.appfilm.presentation.ui.CustomResultDialog
import com.example.appfilm.presentation.ui.CustomTextField
import com.example.appfilm.presentation.ui.CustomTextTitle
import com.example.appfilm.presentation.ui.login.components.CustomForgotPasswordText
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("ContextCastToActivity")
@Composable
fun DetailMovieScreen(navController: NavController, movieSlug : String){

    var isHideUi by rememberSaveable { mutableStateOf(false) }

    val activity = LocalContext.current as? ComponentActivity

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isHideUi = true
                Log.d(Constants.STATUS_TAG, "handleOnBackPressed")
                remove()
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    DisposableEffect(Unit) {
        activity?.onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CustomRandomBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(movieSlug)
        }




        }




}