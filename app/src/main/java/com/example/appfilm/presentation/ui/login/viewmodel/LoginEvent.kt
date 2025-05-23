package com.example.appfilm.presentation.ui.login.viewmodel

sealed class LoginEvent {
    data class UpdateIsShowEmailDialog(val email: Boolean) : LoginEvent()

    data class UpdateErrorTextLogin(val text: String) : LoginEvent()

    data class UpdateErrorTextSendEmail(val text: String) : LoginEvent()

    data class UpdateEmail(val newEmail: String) : LoginEvent()

    data class UpdatePassword(val newPassword: String) : LoginEvent()

    data object Login : LoginEvent()

    data object ResendEmail : LoginEvent()

}