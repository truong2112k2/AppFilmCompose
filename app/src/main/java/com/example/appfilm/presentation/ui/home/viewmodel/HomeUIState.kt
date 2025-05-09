package com.example.appfilm.presentation.ui.home.viewmodel

data class HomeUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)