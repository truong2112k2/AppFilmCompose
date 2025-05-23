package com.example.appfilm.presentation.ui.login.viewmodel

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel() {

    private val _logInUISate = MutableStateFlow<UIState>(UIState())
    val logInUIState: StateFlow<UIState> = _logInUISate

    private val _sendEmailUIState = MutableStateFlow<UIState>(UIState())
    val sendEmailUIState: StateFlow<UIState> = _sendEmailUIState

    var logInFields by mutableStateOf(LoginFields())


    private fun updateIsShowEmailDialog(newValue: Boolean) {
        logInFields = logInFields.copy(
            isShowSendEmailDialog = newValue
        )
    }

    private fun updateErrorTextLogin(newError: String) {
        logInFields = logInFields.copy(
            errorTextLogin = newError
        )
    }

    private fun updateErrorTextSendEmail(newError: String) {
        logInFields = logInFields.copy(
            errorTextSendEmail = newError
        )
    }


    private fun updateEmail(newEmail: String) {
        logInFields = logInFields.copy(inputEmail = newEmail)
    }

    private fun updatePassword(newPassword: String) {
        logInFields = logInFields.copy(inputPassword = newPassword)
    }

    fun login() {
        val email = logInFields.inputEmail
        val password = logInFields.inputPassword



        viewModelScope.launch(Dispatchers.Default) {
            Log.d(Constants.STATUS_TAG, "Login coroutine started")

            val emailError = appUseCases.validationUseCase.validationEmail(email)

            if (emailError != null) {
                Log.e(Constants.ERROR_TAG, "Email validation error: $emailError")
                _logInUISate.value = UIState(error = emailError)
                updateErrorTextLogin(emailError)
                return@launch
            }

            val passwordError = appUseCases.validationUseCase.validationPasswordLogin(password)
            if (passwordError != null) {
                Log.e(Constants.ERROR_TAG, "Password validation error: $passwordError")
                _logInUISate.value = UIState(error = passwordError)
                updateErrorTextLogin(passwordError)
                return@launch
            }

            appUseCases.logInUseCase.invoke(email, password).collect { result ->
                _logInUISate.value = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Login Loading")
                        UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        if (result.data == true) {
                            Log.d(Constants.STATUS_TAG, "Login Success")
                            UIState(isSuccess = true)
                        } else {
                            Log.e(Constants.ERROR_TAG, "Email not verified")
                            updateErrorTextLogin("Email not verified")
                            UIState(error = "Email not verified")
                        }
                    }

                    is Resource.Error -> {
                        val error = convertLoginException(result.exception ?: Exception())
                        Log.e(Constants.ERROR_TAG, "Login Error: $error")
                        updateErrorTextLogin(error)
                        UIState(error = error)
                    }
                }
            }
        }
    }

    private fun resendEmail() {

        viewModelScope.launch {
            Log.d(Constants.STATUS_TAG, "Resend email verification started")

            appUseCases.sendEmailVerificationUseCase.invoke().collect { result ->
                Log.d(Constants.STATUS_TAG, "Collecting resend email result...")

                _sendEmailUIState.value = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Resend email loading...")
                        UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d(
                            Constants.STATUS_TAG,
                            "Resend email success: A verification has been sent."
                        )
                        updateErrorTextSendEmail("A verification has been sent, please check your email !")
                        updateIsShowEmailDialog(true)
                        UIState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        val error =
                            convertSendEmailException(result.exception, fallback = result.message)
                        Log.e(Constants.ERROR_TAG, "Resend email error: $error")
                        updateErrorTextSendEmail(error)
                        updateIsShowEmailDialog(true)
                        UIState(error = error)
                    }
                }
            }
        }
    }


    fun handleEventLogin(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.UpdateIsShowEmailDialog -> {
                updateIsShowEmailDialog(loginEvent.email)
            }

            is LoginEvent.UpdateErrorTextLogin -> {
                updateErrorTextLogin(loginEvent.text)
            }

            is LoginEvent.UpdateErrorTextSendEmail -> {
                updateErrorTextSendEmail(loginEvent.text)
            }

            is LoginEvent.UpdateEmail -> {
                updateEmail(loginEvent.newEmail)
            }

            is LoginEvent.UpdatePassword -> {
                updatePassword(loginEvent.newPassword)
            }

            is LoginEvent.Login -> {
                login()
            }

            is LoginEvent.ResendEmail -> {
                resendEmail()
            }
        }
    }


    private fun convertLoginException(e: Exception): String {
        return when (e) {
            is FirebaseAuthInvalidUserException -> {
                "Account does not exist."
            }

            is FirebaseAuthInvalidCredentialsException -> {
                "Incorrect email or password."
            }

            is FirebaseAuthException -> {
                "Authentication error: ${e.message}"
            }

            is FirebaseNetworkException -> {
                "No internet connection."
            }

            else -> {
                e.localizedMessage ?: "An unknown error has occurred."
            }
        }
    }


}