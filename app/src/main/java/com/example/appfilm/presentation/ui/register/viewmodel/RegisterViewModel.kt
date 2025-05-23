package com.example.appfilm.presentation.ui.register.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.UIState
import com.example.appfilm.presentation.ui.convertSendEmailException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appUseCases: AppUseCases

) : ViewModel() {


    var registerState by mutableStateOf(UIState())
    var sendEmailUIState by mutableStateOf(UIState())

    var registerFields by mutableStateOf(RegisterFields())


    private fun updateResultTextSendEmail(newResult: String) {
        registerFields = registerFields.copy(
            resultTextSendEmail = newResult
        )
    }

    fun toggleIsShowDialogSuccess() {
        registerFields = registerFields.copy(
            isShowDialogSuccess = !registerFields.isShowDialogSuccess
        )
    }

    fun updateErrorTextRegister(error: String) {
        registerFields = registerFields.copy(
            errorTextRegister = error
        )
    }

    fun updateEmail(newEmail: String) {
        registerFields = registerFields.copy(
            inputEmail = newEmail
        )
    }

    fun updatePassword(newPassword: String) {
        registerFields = registerFields.copy(
            inputPassword = newPassword
        )
    }

    fun updateReInputPassword(newPassword: String) {
        registerFields = registerFields.copy(
            reInputPassword = newPassword
        )
    }

    fun reset(level: Int) {
        when (level) {
            1 -> registerFields = RegisterFields()
            2 -> registerState = UIState()
            3 -> {
                registerFields = RegisterFields()
                registerState = UIState()
            }
        }
    }

    fun register() {
        viewModelScope.launch(Dispatchers.Default) {

            Log.d(Constants.STATUS_TAG, "Register coroutine started")

            val email = registerFields.inputEmail
            val password = registerFields.inputPassword
            val rePassword = registerFields.reInputPassword
            Log.d(Constants.STATUS_TAG, "Input email: $email")

            val emailError = appUseCases.validationUseCase.validationEmail(email)
            if (emailError != null) {
                Log.e(Constants.ERROR_TAG, "Email validation error: $emailError")
                registerState = UIState(error = emailError)
                updateErrorTextRegister(error = emailError)
                return@launch
            }

            val passwordError =
                appUseCases.validationUseCase.validationPasswordRegister(password, rePassword)
            if (passwordError != null) {
                Log.e(Constants.ERROR_TAG, "Password validation error: $passwordError")
                registerState = UIState(error = passwordError)
                updateErrorTextRegister(error = passwordError)
                return@launch
            }

            appUseCases.registerUseCase(email, password).collect { result ->
                Log.d(Constants.STATUS_TAG, "Collecting register result...")

                registerState = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Register Loading...")
                        UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d(Constants.STATUS_TAG, "Register Success")
                        UIState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        val message = convertRegisterException(result.exception)
                        Log.e(Constants.ERROR_TAG, "Register Error: $message")
                        updateErrorTextRegister(error = message)
                        UIState(error = message)
                    }
                }
            }
        }
    }

    fun resendEmail() {
        viewModelScope.launch {
            Log.d(Constants.STATUS_TAG, "Resend email verification started")

            appUseCases.sendEmailVerificationUseCase.invoke().collect { result ->
                Log.d(Constants.STATUS_TAG, "Collecting resend email result...")

                sendEmailUIState = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Resend email loading...")
                        UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d(
                            Constants.STATUS_TAG,
                            "Resend email success: A verification has been sent."
                        )
                        updateResultTextSendEmail("A verification has been sent, please check your email !")
                        UIState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        val error =
                            convertSendEmailException(result.exception, fallback = result.message)
                        Log.e(Constants.ERROR_TAG, "Resend email error: $error")
                        updateResultTextSendEmail(error)
                        UIState(error = error)
                    }
                }
            }
        }
    }


    private fun convertRegisterException(e: Exception?): String {
        return when (e) {
            is FirebaseAuthUserCollisionException -> "Email is already in use"
            is FirebaseNetworkException -> "Network error, please check your connection"
            is FirebaseAuthException -> "Error: ${e.message}"
            else -> e?.message ?: "An unknown error occurred"
        }
    }


    fun handleEvent(registerEvent: RegisterEvent) {
        when (registerEvent) {
            is RegisterEvent.Register -> {
                register()
            }

            is RegisterEvent.UpdateResultTextSendEmail -> {
                updateResultTextSendEmail(registerEvent.newResult)
            }

            is RegisterEvent.ToggleIsShowDialogSuccess -> {

                toggleIsShowDialogSuccess()

            }

            is RegisterEvent.UpdateErrorTextRegister -> {
                updateErrorTextRegister(registerEvent.error)
            }

            is RegisterEvent.UpdateEmail -> {
                updateEmail(registerEvent.newEmail)
            }

            is RegisterEvent.UpdatePassword -> {
                updatePassword(registerEvent.newPassword)
            }

            is RegisterEvent.UpdateReInputPassword -> {
                updateReInputPassword(registerEvent.newPassword)
            }

            is RegisterEvent.Reset -> {
                reset(registerEvent.level)
            }

            is RegisterEvent.ResendEmail -> {
                resendEmail()
            }
        }
    }

}