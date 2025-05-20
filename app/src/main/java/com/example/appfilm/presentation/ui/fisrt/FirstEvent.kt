package com.example.appfilm.presentation.ui.fisrt

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class FirstEvent(){

    data class SignInWithGoogle(val idToken: String) : FirstEvent()

    data object CheckLogin : FirstEvent()

}