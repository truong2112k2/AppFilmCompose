package com.example.appfilm.presentation.ui.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.login.LogInUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val appUseCases: AppUseCases
) : ViewModel()  {

    var uiState by mutableStateOf(LogInUIState())

    fun login(email: String, password: String){
        viewModelScope.launch(Dispatchers.Default) {
            appUseCases.logInUseCase.invoke(email, password).collect{ result ->
                uiState = when(result){
                    is Resource.Loading -> LogInUIState(isLoading =  true )
                    is Resource.Success -> LogInUIState(isSuccess =  true )
                    is Resource.Error -> LogInUIState(error = result.message )
                }
            }

        }
    }

}