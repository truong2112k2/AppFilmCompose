package com.example.appfilm.presentation.ui.reset_password.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ResetPassViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel() {

    var resetPassFields by mutableStateOf(ResetPassFields())
    var resetPassState by mutableStateOf(ResetPassUIState())


    fun updateEmail(newEmail: String) {
        resetPassFields = resetPassFields.copy(
            email = newEmail
        )
    }

    fun updateResultString(newString: String){
        resetPassFields = resetPassFields.copy(
            resultString = newString
        )
    }

    fun updateIsShowDialogResult(newValue: Boolean){
        resetPassFields = resetPassFields.copy(
            isShowDialogResult = newValue
        )
    }


    fun resetPassword() {

        viewModelScope.launch(Dispatchers.IO) {


            val errorEmail = appUseCases.validationUseCase.validationEmail(resetPassFields.email)
            if (errorEmail?.isNotEmpty() == true) {
                resetPassState = ResetPassUIState(error = errorEmail)
                updateResultString(errorEmail)

                return@launch
            }

            appUseCases.resetPassWordUseCase.invoke(resetPassFields.email).collect { result ->
                resetPassState = when (result) {
                    is Resource.Loading -> {
                        ResetPassUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        updateResultString("Check ${resetPassFields.email} to confirm reset password email")

                        updateIsShowDialogResult(true)

                        ResetPassUIState(isSuccess = true)

                    }

                    is Resource.Error -> {

                        result.message?.let {
                            updateResultString(it)
                        }
                        updateIsShowDialogResult(true)
                        ResetPassUIState(
                            error = result.message
                        )
                    }
                }

            }


        }

    }

    fun handleEvent(registerEvent: RegisterEvent){
        when(registerEvent){
            is RegisterEvent.updateEmail -> { updateEmail(registerEvent.email)}
            is RegisterEvent.resetPassword -> { resetPassword() }
            is RegisterEvent.updateIsShowDialogResult -> { updateIsShowDialogResult(registerEvent.value)}
        }
    }




}