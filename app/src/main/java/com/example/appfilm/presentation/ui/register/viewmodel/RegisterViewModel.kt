package com.example.appfilm.presentation.ui.register.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appUseCases: AppUseCases

) : ViewModel() {


    var registerState by mutableStateOf(RegisterUIState())
    var sendEmailUIState by mutableStateOf(RegisterUIState())

    var registerFields by mutableStateOf(RegisterFields())


    fun updateResultTextSendEmail(newResult: String){
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
            2 -> registerState = RegisterUIState()
            3 -> {
                registerFields = RegisterFields()
                registerState = RegisterUIState()
            }
        }
    }


    fun register() {

        viewModelScope.launch(Dispatchers.Default) {


            val email = registerFields.inputEmail
            val password = registerFields.inputPassword
            val rePassword = registerFields.reInputPassword
            val emailError = appUseCases.validationUseCase.validationEmail(email)

            if (emailError != null) {
                registerState = RegisterUIState(error = emailError)
                updateErrorTextRegister(error = emailError)
                return@launch
            }

            val passwordError =
                appUseCases.validationUseCase.validationPasswordRegister(password, rePassword)
            if (passwordError != null) {
                registerState = RegisterUIState(error = passwordError)
                updateErrorTextRegister(error = passwordError)

                return@launch
            }

            appUseCases.registerUseCase(email, password).collect { result ->

                registerState = when (result) {
                    is Resource.Loading -> RegisterUIState(isLoading = true)
                    is Resource.Success -> RegisterUIState(success = true)
                    is Resource.Error -> {
                        val message = handleAuthException(result.exception)

                        updateErrorTextRegister(error = message)

                        RegisterUIState(error = message)

                    }
                }

            }
        }
    }


    fun resendEmail(){
        viewModelScope.launch {
            appUseCases.reSendEmailVerification.invoke().collect{ result->
                sendEmailUIState =   when(result){
                    is Resource.Loading -> {
                        Log.d( "12321","load")
                        RegisterUIState(isLoading = true)
                    }
                    is Resource.Success -> {
                        Log.d( "12321","ok")
                        updateResultTextSendEmail("A verification has been sent, please check your email !")

                        RegisterUIState(success = true )
                    }
                    is Resource.Error -> {
                        Log.d( "12321","Not ok")

                        val error = convertSendEmailException(result.exception, fallback = result.message)
                        updateResultTextSendEmail(error)


                        RegisterUIState(error = error)
                    }
                }
            }
        }
    }
//    fun resendEmail() {
//        viewModelScope.launch {
//            appUseCases.reSendEmailVerification.invoke().collect { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        Log.d("12321", "load")
//                    }
//
//                    is Resource.Success -> {
//                        Log.d("12321", "ok")
//
//                    }
//
//                    is Resource.Error -> {
//                        Log.d("12321", "Not ok")
//
//                    }
//                }
//            }
//        }
//    }


    private fun handleAuthException(e: Exception?): String {
        return when (e) {
            is FirebaseAuthUserCollisionException -> "Email is already in use"
            is FirebaseNetworkException -> "Network error, please check your connection"
            is FirebaseAuthException -> "Error: ${e.message}"
            else -> e?.message ?: "An unknown error occurred"
        }
    }

    fun convertSendEmailException(e: Exception?, fallback: String? = null): String {
        return when (e) {
            is FirebaseAuthInvalidUserException -> "User does not exist"
            is FirebaseNetworkException -> "Network connection error"
            is FirebaseAuthException -> "Authentication error: ${e.message}"
            else -> fallback ?: e?.message ?: "An unknown error occurred"
        }
    }

}