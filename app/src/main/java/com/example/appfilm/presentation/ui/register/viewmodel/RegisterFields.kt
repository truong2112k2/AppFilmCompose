package com.example.appfilm.presentation.ui.register.viewmodel

data class RegisterFields(
    val inputEmail: String = "",
    val inputPassword: String = "",
    val reInputPassword: String = "",
    val errorText: String = "",
    val isShowDialogSuccess: Boolean = false,
)