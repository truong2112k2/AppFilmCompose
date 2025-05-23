package com.example.appfilm.presentation.ui.home.viewmodel

import com.google.android.gms.auth.api.signin.GoogleSignInClient

sealed class HomeEvent {
    data class Logout(val googleSignInClient: GoogleSignInClient) : HomeEvent()

}