package com.example.appfilm.presentation.ui.login.viewmodel

data class LoginUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)