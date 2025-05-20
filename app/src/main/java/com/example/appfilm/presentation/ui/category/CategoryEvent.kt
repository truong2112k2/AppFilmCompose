package com.example.appfilm.presentation.ui.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.appfilm.common.Constants
import com.example.appfilm.domain.model.MovieByCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

sealed class CategoryEvent {


    data object GetCategory : CategoryEvent()

    data class GetMoviesByCategory( val typeList: String): CategoryEvent()
}