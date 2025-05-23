package com.example.appfilm.presentation.ui.fisrt.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FirstViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel() {


    private val _loginWithoutPassState = MutableStateFlow<FirstUiState>(FirstUiState())
    val loginWithoutPassState: StateFlow<FirstUiState> = _loginWithoutPassState

    private val _checkLoginState = MutableStateFlow<FirstUiState>(FirstUiState())
    val checkLoginState: StateFlow<FirstUiState> = _checkLoginState


    private fun signInWithGoogle(idToken: String) {

        viewModelScope.launch {


            appUseCases.logInWithoutPassUseCase.invoke(idToken).collect { result ->

                _loginWithoutPassState.value = when (result) {

                    is Resource.Loading -> {
                        Log.d("FirstViewModel", "LoginWithoutMail loading")

                        FirstUiState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d("FirstViewModel", "LoginWithoutMail success ")

                        FirstUiState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d("FirstViewModel", "LoginWithoutMail ${result.message.toString()}")
                        FirstUiState(
                            error = convertLoginGoogleException(
                                result.exception ?: Exception()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun checkLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            _checkLoginState.value = FirstUiState(isLoading = true)

            delay(3000)
            appUseCases.checkLoginUseCase.invoke().collect { result ->

                _checkLoginState.value = when (result) {
                    is Resource.Loading -> {
                        Log.d("FirstViewModel", "checkLogin Loading")

                        FirstUiState(isLoading = true)

                    }

                    is Resource.Success -> {


                        Log.d("FirstViewModel", "checkLogin Success")

                        FirstUiState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d("FirstViewModel", "CheckLogin failed")

                        FirstUiState(error = result.message)

                    }

                }


            }


        }

    }

    private fun convertLoginGoogleException(e: Exception): String {
        return when (e) {
            is FirebaseAuthInvalidUserException -> "Account does not exist."
            is FirebaseAuthInvalidCredentialsException -> "Incorrect email or password."
            is FirebaseNetworkException -> "No internet connection."
            is FirebaseAuthException -> "Authentication error: ${e.message}"
            is ApiException -> when (e.statusCode) {
                7 -> "Network error. Please check your internet connection."
                10 -> "App is not configured properly for Google Sign-In."
                12501 -> "Sign-in was cancelled."
                else -> "Google Sign-In failed with error code: ${e.statusCode}"
            }

            else -> e.localizedMessage ?: "An unknown error has occurred."
        }
    }


    fun handleEvent(firstEvent: FirstEvent) {
        when (firstEvent) {
            is FirstEvent.CheckLogin -> {
                checkLogin()
            }

            is FirstEvent.SignInWithGoogle -> {
                signInWithGoogle(firstEvent.idToken)
            }
        }
    }


}