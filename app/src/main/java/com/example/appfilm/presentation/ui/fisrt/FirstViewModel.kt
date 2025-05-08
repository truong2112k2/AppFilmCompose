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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FirstViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel(){


    private val _firstUiState = MutableStateFlow<FirstUiState>(FirstUiState())
    val firstUiState: StateFlow<FirstUiState> = _firstUiState


    var isShowDialogResult by mutableStateOf(false )
    var resultText by mutableStateOf("")

    fun updateIsShowDialogResult(newValue: Boolean){
        isShowDialogResult = newValue
    }
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
             appUseCases.logInWithoutPassUseCase.firebaseSignInWithGoogle(idToken).collect{ result->

                 _firstUiState.value = when(result){
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
}