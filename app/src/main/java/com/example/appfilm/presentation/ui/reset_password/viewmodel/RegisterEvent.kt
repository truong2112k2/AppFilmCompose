package com.example.appfilm.presentation.ui.reset_password.viewmodel

sealed class RegisterEvent (){
    data class UpdateEmail(val email: String) : RegisterEvent()
    data object ResetPassword : RegisterEvent()
    data class UpdateIsShowDialogResult(val value : Boolean) : RegisterEvent()


}