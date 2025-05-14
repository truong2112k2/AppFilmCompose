package com.example.appfilm.presentation.ui.category

data class CategoryUIState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)