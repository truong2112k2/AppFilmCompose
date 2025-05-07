package com.example.appfilm.presentation.ui.register.viewmodel

data class RegisterFields(
    val inputEmail: String = "",
    val inputPassword: String = "",
    val reInputPassword: String = "",
    val errorTextRegister: String = "",
    val isShowDialogSuccess: Boolean = false,

    val resultTextSendEmail: String = "",
    //val isShowErrorTextSendEmail: Boolean = false,
)