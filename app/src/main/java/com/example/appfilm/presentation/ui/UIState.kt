package com.example.appfilm.presentation.ui

data class UIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
) {
}