package com.example.appfilm.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class HomeEvent (){
    data class Logout(val googleSignInClient: GoogleSignInClient): HomeEvent()
}