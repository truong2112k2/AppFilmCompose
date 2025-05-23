package com.example.appfilm.presentation.ui.category.viewmodel

sealed class CategoryEvent {

    data object GetCategory : CategoryEvent()

    data class GetMoviesByCategory(val typeList: String) : CategoryEvent()

}