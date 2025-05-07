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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel()  {

    var logInUIState by mutableStateOf(LogInUIState())
    var sendEmailUIState by mutableStateOf(LogInUIState())

    var logInFields by mutableStateOf(LogInFields())





    fun updateIsShowEmailDialog(newValue: Boolean){
        logInFields = logInFields.copy(
            isShowSendEmailDialog = newValue
        )
    }
    fun updateErrorTextLogin(newError: String){
        logInFields = logInFields .copy(
            errorTextLogin = newError
        )
    }
    fun updateErrorTextSendEmail(newError: String){
        logInFields = logInFields .copy(
            errorTextSendEmail = newError
        )
    }


    fun updateEmail(newEmail: String){
        logInFields = logInFields.copy(inputEmail = newEmail)
    }

    fun updatePassword(newPassword: String){
        logInFields = logInFields.copy(inputPassword = newPassword)
    }
    fun login() {
        val email = logInFields.inputEmail
        val password = logInFields.inputPassword

        Log.d(Constants.STATUS_TAG, "Input email: $email")
        Log.d(Constants.STATUS_TAG, "Input password length: ${password.length} characters")

        viewModelScope.launch(Dispatchers.Default) {
            Log.d(Constants.STATUS_TAG, "Login coroutine started")

            appUseCases.logInUseCase.invoke(email, password).collect { result ->

                Log.d(Constants.STATUS_TAG, "Collecting login result...")

                val emailError = appUseCases.validationUseCase.validationEmail(email)
                if (emailError != null) {
                    Log.e(Constants.ERROR_TAG, "Email validation error: $emailError")
                    logInUIState = LogInUIState(error = emailError)
                    updateErrorTextLogin(emailError)
                    return@collect
                }

                val passwordError = appUseCases.validationUseCase.validationPasswordLogin(password)
                if (passwordError != null) {
                    Log.e(Constants.ERROR_TAG, "Password validation error: $passwordError")
                    logInUIState = LogInUIState(error = passwordError)
                    updateErrorTextLogin(passwordError)
                    return@collect
                }

                logInUIState = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Login Loading")
                        LogInUIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        if (result.data == true) {
                            Log.d(Constants.STATUS_TAG, "Login Success")
                            LogInUIState(isSuccess = true)
                        } else {
                            Log.e(Constants.ERROR_TAG, "Email not verified")
                            updateErrorTextLogin("Email not verified")
                            LogInUIState(error = "Email not verified")
                        }
                    }

                    is Resource.Error -> {
                        val error = convertLoginException(result.exception ?: Exception())
                        Log.e(Constants.ERROR_TAG, "Login Error: $error")
                        updateErrorTextLogin(error)
                        LogInUIState(error = error)
                    }
                }
            }
        }
    }

    fun resendEmail() {
        viewModelScope.launch {
            Log.d(Constants.STATUS_TAG, "Resend email verification started")

            appUseCases.reSendEmailVerification.invoke().collect { result ->
                Log.d(Constants.STATUS_TAG, "Collecting resend email result...")

                sendEmailUIState = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Resend email loading...")
                        LogInUIState(isLoading = true)
                    }
                    is Resource.Success -> {
                        Log.d(Constants.STATUS_TAG, "Resend email success: A verification has been sent.")
                        updateErrorTextSendEmail("A verification has been sent, please check your email !")
                        updateIsShowEmailDialog(true)
                        LogInUIState(isSuccess = true)
                    }
                    is Resource.Error -> {
                        val error = convertSendEmailException(result.exception, fallback = result.message)
                        Log.e(Constants.ERROR_TAG, "Resend email error: $error")
                        updateErrorTextSendEmail(error)
                        updateIsShowEmailDialog(true)
                        LogInUIState(error = error)
                    }
                }
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
                "No internet connection. Please check your network."
            }
            else -> {
                e.localizedMessage ?: "An unknown error has occurred."
            }
        }
    }





}