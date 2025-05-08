package com.example.appfilm.presentation.ui.reset_password.viewmodel

data class ResetPassUIState (
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)