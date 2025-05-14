package com.example.appfilm.presentation.ui.category

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.domain.usecase.AppUseCases
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

    private val _getCategoryState = MutableStateFlow(CategoryUIState())
    val getCategoryState : StateFlow<CategoryUIState> = _getCategoryState

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories : StateFlow<List<Category>> = _categories

    private val _getMoviesByCategoryState = MutableStateFlow(CategoryUIState())
    val getMoviesByCategoryState : StateFlow<CategoryUIState> = _getMoviesByCategoryState

    private val _moviesByCategory = MutableStateFlow(emptyList<MovieByCategory>())
    val moviesByCategory : StateFlow<List<MovieByCategory>> = _moviesByCategory

    fun getCategory(){

        viewModelScope.launch(Dispatchers.IO) {
            val result = appUseCases.getCategoryUseCase.fetchCategory()

            if(result.data?.isNotEmpty() == true){

                _categories.value = result.data
                _getCategoryState.value = CategoryUIState(isSuccess = true)
                Log.d(Constants.STATUS_TAG,"getCategory UseCase success")


            }else{
                Log.d(Constants.STATUS_TAG,"getCategory UseCase Failed")

                _getCategoryState.value = CategoryUIState(error = "Failed")


            }
        }
    }

    fun getMoviesByCategory(category: String){
        _getMoviesByCategoryState.value = CategoryUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = appUseCases.getMoviesByCategoryUseCase.invoke(category)

            if(result.data?.isNotEmpty() == true){

                _moviesByCategory.value = result.data

                _getMoviesByCategoryState.value = CategoryUIState(isSuccess = true)
                Log.d(Constants.STATUS_TAG,"get movies Category UseCase success")


            }else{
                Log.d(Constants.STATUS_TAG,"get movies Category UseCase Failed ${result.message} slug = ${category}")
                _getMoviesByCategoryState.value = CategoryUIState(error = result.message)



            }
        }
    }

}