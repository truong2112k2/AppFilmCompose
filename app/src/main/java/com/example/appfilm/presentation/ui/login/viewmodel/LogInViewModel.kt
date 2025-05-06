package com.example.appfilm.presentation.ui.login.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.login.LogInUIState
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

    var logInFields by mutableStateOf(LogInFields())



    fun updateEmail(newEmail: String){
        logInFields = logInFields.copy(inputEmail = newEmail)
    }

    fun updatePassword(newPassword: String){
        logInFields = logInFields.copy(inputPassword = newPassword)
    }

    fun login(email: String, password: String){
        viewModelScope.launch(Dispatchers.Default) {



            appUseCases.logInUseCase.invoke(email, password).collect{ result ->


                val emailError = appUseCases.validationUseCase.validationEmail(email)
                if(emailError != null ){
                    logInUIState = LogInUIState(error = emailError)
                    return@collect
                }
                logInUIState = when(result){
                    is Resource.Loading -> LogInUIState(isLoading =  true )

                    is Resource.Success -> {
                        if(result.data == true){
                            LogInUIState(isSuccess = true )
                        }else{
                            LogInUIState(error = "Email not verified")
                        }
                    }
                    is Resource.Error -> {
                        val message = convertLoginException(result.exception ?: Exception())
                        LogInUIState(error = message )

                    }

                }
            }

        }
    }

    fun resendEmail(){
        viewModelScope.launch {
            appUseCases.reSendEmailVerification.invoke().collect{ result->
                when(result){
                    is Resource.Loading -> {
                        Log.d( "12321","load")
                    }
                    is Resource.Success -> {
                        Log.d( "12321","ok")

                    }
                    is Resource.Error -> {
                        Log.d( "12321","Not ok")

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