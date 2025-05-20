package com.example.appfilm.presentation.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.presentation.ui.convertSendEmailException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class LoginEvent() {
    data class updateIsShowEmailDialog( val email: Boolean) : LoginEvent()

    data class updateErrorTextLogin(val text: String) : LoginEvent()

    data class updateErrorTextSendEmail(val text: String) : LoginEvent()

    data class updateEmail(val newEmail: String) : LoginEvent()

    data class updatePassword(val newPassword: String): LoginEvent()

    object login: LoginEvent()

    object resendEmail : LoginEvent()

}