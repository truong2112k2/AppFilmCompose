package com.example.appfilm.presentation.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.register.RegisterUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel(
    private val appUseCases: AppUseCases
): ViewModel() {

    var uiState = RegisterUIState()

    fun register(email: String, password: String){

        viewModelScope.launch(Dispatchers.Default) {

            appUseCases.registerUseCase(email, password).collect {result ->

                uiState = when(result){

                    is Resource.Loading ->  RegisterUIState(loading = true)

                    is Resource.Success ->  RegisterUIState(success = true)

                    is Resource.Error ->  RegisterUIState(error = result.message)

                }

            }
        }



    }




}