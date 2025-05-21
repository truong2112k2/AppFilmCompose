package com.example.appfilm.presentation.ui.category.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.category.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    private val moviesByCategory : StateFlow<List<MovieByCategory>> = _moviesByCategory

    private fun getCategory(){

        viewModelScope.launch(Dispatchers.IO) {
            val result = appUseCases.fetchCategoryUseCase.fetchCategory()

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

    fun getMoviesByCategory(typeList: String): Flow<PagingData<MovieByCategory>> {


        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { MoviePagingSource( appUseCases,  typeList) }


        ).flow.cachedIn(viewModelScope)



    }

    fun handleEvent(categoryEvent: CategoryEvent){
        when(categoryEvent){
            is CategoryEvent.GetCategory -> {getCategory()}
            is CategoryEvent.GetMoviesByCategory ->{

                    getMoviesByCategory(categoryEvent.typeList)


            }
        }
    }



}