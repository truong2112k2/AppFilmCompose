package com.example.appfilm.presentation.ui.fisrt.viewmodel

sealed class FirstEvent {

    data class SignInWithGoogle(val idToken: String) : FirstEvent()

    data object CheckLogin : FirstEvent()

}