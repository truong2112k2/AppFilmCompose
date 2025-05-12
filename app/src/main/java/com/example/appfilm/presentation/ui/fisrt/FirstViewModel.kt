package com.example.appfilm.presentation.ui.fisrt

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class FirstViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel(){


    private val _loginWithoutPassState = MutableStateFlow<FirstUiState>(FirstUiState())
    val loginWithoutPassState: StateFlow<FirstUiState> = _loginWithoutPassState

    private val _checkLoginState = MutableStateFlow<FirstUiState>(FirstUiState())
    val checkLoginState : StateFlow<FirstUiState> = _checkLoginState


    var isShowDialogResult by mutableStateOf(false )
    var resultText by mutableStateOf("")

    fun updateIsShowDialogResult(newValue: Boolean){
        isShowDialogResult = newValue
    }
    fun signInWithGoogle(idToken: String) {
        Log.d("LoginWithoutMail", "Goi ham")

        viewModelScope.launch {


            appUseCases.logInWithoutPassUseCase.invoke(idToken).collect{ result->

                 _loginWithoutPassState.value = when(result){
                     is Resource.Loading -> {
                         Log.d("LoginWithoutMail", "loading")

                         FirstUiState(isLoading = true)
                     }
                     is Resource.Success -> {
                         Log.d("LoginWithoutMail", "success LoginWithoutMail ")

                         FirstUiState(isSuccess = true)
                     }
                     is Resource.Error -> {
                         Log.d("LoginWithoutMail", result.message.toString())
                         resultText = result.message.toString()
                         FirstUiState(error = "fail")
                     }
                 }
             }
        }
    }

    fun checkLogin(
        onLogin : () -> Unit
    ){
        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.checkLoginUseCase.invoke().collect{ result ->

                _checkLoginState.value = when(result) {
                    is Resource.Loading -> {
                        Log.d("CheckLogin", "Load")

                        FirstUiState(isLoading = true)

                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main){
                            onLogin()
                        }

                        Log.d("CheckLogin","Success")

                        FirstUiState(isSuccess = true)
                    }
                    is Resource.Error -> {
                        Log.d("CheckLogin", "Not ok")

                        FirstUiState(error = result.message)

                    }

            }


            }




        }

    }




}