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

    private val _logInUISate = MutableStateFlow<LoginUIState>(LoginUIState())
    val logInUIState: StateFlow<LoginUIState> = _logInUISate

    private val _sendEmailUIState = MutableStateFlow<LoginUIState>(LoginUIState())
    val sendEmailUIState: StateFlow<LoginUIState> = _sendEmailUIState

    var logInFields by mutableStateOf(LoginFields())


    fun updateIsShowEmailDialog(newValue: Boolean) {
        logInFields = logInFields.copy(
            isShowSendEmailDialog = newValue
        )
    }

    fun updateErrorTextLogin(newError: String) {
        logInFields = logInFields.copy(
            errorTextLogin = newError
        )
    }

    fun updateErrorTextSendEmail(newError: String) {
        logInFields = logInFields.copy(
            errorTextSendEmail = newError
        )
    }


    fun updateEmail(newEmail: String) {
        logInFields = logInFields.copy(inputEmail = newEmail)
    }

    fun updatePassword(newPassword: String) {
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
                _logInUISate.value = LoginUIState(error = emailError)
                updateErrorTextLogin(emailError)
                return@launch
            }

            val passwordError = appUseCases.validationUseCase.validationPasswordLogin(password)
            if (passwordError != null) {
                Log.e(Constants.ERROR_TAG, "Password validation error: $passwordError")
                _logInUISate.value = LoginUIState(error = passwordError)
                updateErrorTextLogin(passwordError)
                return@launch
            }

            appUseCases.logInUseCase.invoke(email, password).collect { result ->
                _logInUISate.value = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Login Loading")
                        LoginUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        if (result.data == true) {
                            Log.d(Constants.STATUS_TAG, "Login Success")
                            LoginUIState(isSuccess = true)
                        } else {
                            Log.e(Constants.ERROR_TAG, "Email not verified")
                            updateErrorTextLogin("Email not verified")
                            LoginUIState(error = "Email not verified")
                        }
                    }

                    is Resource.Error -> {
                        val error = convertLoginException(result.exception ?: Exception())
                        Log.e(Constants.ERROR_TAG, "Login Error: $error")
                        updateErrorTextLogin(error)
                        LoginUIState(error = error)
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

                _sendEmailUIState.value = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Resend email loading...")
                        LoginUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d(
                            Constants.STATUS_TAG,
                            "Resend email success: A verification has been sent."
                        )
                        updateErrorTextSendEmail("A verification has been sent, please check your email !")
                        updateIsShowEmailDialog(true)
                        LoginUIState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        val error =
                            convertSendEmailException(result.exception, fallback = result.message)
                        Log.e(Constants.ERROR_TAG, "Resend email error: $error")
                        updateErrorTextSendEmail(error)
                        updateIsShowEmailDialog(true)
                        LoginUIState(error = error)
                    }
                }
            }
        }
    }


    fun handleEventLogin(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.updateIsShowEmailDialog -> {
                updateIsShowEmailDialog(loginEvent.email)
            }

            is LoginEvent.updateErrorTextLogin -> {
                updateErrorTextLogin(loginEvent.text)
            }

            is LoginEvent.updateErrorTextSendEmail -> {
                updateErrorTextSendEmail(loginEvent.text)
            }

            is LoginEvent.updateEmail -> {
                updateEmail(loginEvent.newEmail)
            }

            is LoginEvent.updatePassword -> {
                updatePassword(loginEvent.newPassword)
            }

            is LoginEvent.login -> {
                login()
            }

            is LoginEvent.resendEmail -> {
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