package com.example.appfilm.presentation.ui.login.viewmodel

data class LogInFields(
    val inputEmail: String = "",
    val inputPassword: String = "",
    val errorTextLogin: String = "",
    val errorTextSendEmail: String = "",
    val isShowSendEmailDialog: Boolean = false,


)