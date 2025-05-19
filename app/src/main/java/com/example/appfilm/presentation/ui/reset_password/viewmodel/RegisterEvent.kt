package com.example.appfilm.presentation.ui.reset_password.viewmodel

sealed class RegisterEvent (){
    data class updateEmail( val email: String) : RegisterEvent()
    object resetPassword : RegisterEvent()
    data class updateIsShowDialogResult( val value : Boolean) : RegisterEvent()


}