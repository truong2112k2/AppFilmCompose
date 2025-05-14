package com.example.appfilm.presentation.ui.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.home.viewmodel.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val appUseCases: AppUseCases
): ViewModel(){

    private val _categoryUIState = MutableStateFlow(CategoryUIState())
    val categoryUIState : StateFlow<CategoryUIState> = _categoryUIState

    private val _listCategory = MutableStateFlow(emptyList<Category>())
    val listCategory : StateFlow<List<Category>> = _listCategory


    fun getCategory(){

        viewModelScope.launch(Dispatchers.IO) {
            val result = appUseCases.getCategoryUseCase.fetchCategory()

            if(result.data?.isNotEmpty() == true){

                _listCategory.value = result.data
                _categoryUIState.value = CategoryUIState(isSuccess = true)
                Log.d(Constants.STATUS_TAG,"getCategory UseCase success")


            }else{
                Log.d(Constants.STATUS_TAG,"getCategory UseCase Failed")

                _categoryUIState.value = CategoryUIState(error = "Failed")


            }
        }
    }

}