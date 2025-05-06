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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appUseCases: AppUseCases

): ViewModel() {


    var registerState by mutableStateOf(RegisterUIState())
    var registerFields by mutableStateOf(RegisterFields())





    fun toggleIsShowDialogSuccess(){
        registerFields = registerFields.copy(
            isShowDialogSuccess = !registerFields.isShowDialogSuccess
        )
    }
    fun updateErrorText(error: String){
        registerFields = registerFields.copy(
            errorText = error
        )
    }
    fun updateEmail(newEmail: String){
        registerFields =   registerFields.copy(
            inputEmail =  newEmail
        )
    }

    fun updatePassword(newPassword: String){
        registerFields =  registerFields.copy(
            inputPassword = newPassword
        )
    }

    fun updateReInputPassword(newPassword: String){
        registerFields =  registerFields.copy(
            reInputPassword = newPassword
        )
    }

    fun resetRegisterField(){
        registerFields = RegisterFields()
    }

    fun register(email: String, password: String){

        viewModelScope.launch(Dispatchers.Default) {

            val emailError = appUseCases.validationUseCase.validationEmail(email)

            if( emailError != null ){
                registerState = RegisterUIState(error = emailError)
                updateErrorText(error = emailError)
                return@launch
            }

            val passwordError = appUseCases.validationUseCase.validationPassword(registerFields.inputPassword, registerFields.reInputPassword)
            if( passwordError != null ){
                registerState =   RegisterUIState(error = passwordError)
                updateErrorText(error = passwordError)

                return@launch
            }

            appUseCases.registerUseCase(email, password).collect {result ->

                registerState = when(result){
                    is Resource.Loading ->  RegisterUIState(isLoading = true)
                    is Resource.Success ->  RegisterUIState(success = true)
                    is Resource.Error -> {
                        val message = handleAuthException(result.exception)

                        updateErrorText(error = message)

                        RegisterUIState(error = message)

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


    private fun handleAuthException(e: Exception?): String {
        return when (e) {
            is FirebaseAuthUserCollisionException -> "Email is already in use"
            is FirebaseNetworkException -> "Network error, please check your connection"
            is FirebaseAuthException -> "Error: ${e.message}"
            else -> e?.message ?: "An unknown error occurred"
        }
    }



}