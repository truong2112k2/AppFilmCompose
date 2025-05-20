package com.example.appfilm.presentation.ui.register.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.presentation.ui.convertSendEmailException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class RegisterEvent(){
    data class UpdateResultTextSendEmail(val newResult: String) : RegisterEvent()

    data object ToggleIsShowDialogSuccess : RegisterEvent()

    data class UpdateErrorTextRegister(val error: String) : RegisterEvent()

    data class UpdateEmail(val newEmail: String) : RegisterEvent()

    data class UpdatePassword(val newPassword: String): RegisterEvent()

    data class UpdateReInputPassword(val newPassword: String) : RegisterEvent()
    data class Reset(val level: Int) : RegisterEvent()

    data object Register : RegisterEvent()

    data object ResendEmail : RegisterEvent()
}