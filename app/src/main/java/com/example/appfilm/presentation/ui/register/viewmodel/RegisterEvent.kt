package com.example.appfilm.presentation.ui.register.viewmodel

sealed class RegisterEvent {
    data class UpdateResultTextSendEmail(val newResult: String) : RegisterEvent()

    data object ToggleIsShowDialogSuccess : RegisterEvent()

    data class UpdateErrorTextRegister(val error: String) : RegisterEvent()

    data class UpdateEmail(val newEmail: String) : RegisterEvent()

    data class UpdatePassword(val newPassword: String) : RegisterEvent()

    data class UpdateReInputPassword(val newPassword: String) : RegisterEvent()
    data class Reset(val level: Int) : RegisterEvent()

    data object Register : RegisterEvent()

    data object ResendEmail : RegisterEvent()
}