package com.example.appfilm.presentation.ui.category

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.appfilm.data.source.remote.dto.MovieApiService
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.model.MovieByCategory
import com.example.appfilm.domain.usecase.AppUseCases
import javax.inject.Inject
import javax.inject.Singleton



class MoviePagingSource (
    private val appUseCases: AppUseCases ,
    private val typeList: String
) : PagingSource<Int, MovieByCategory>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieByCategory> {
        return try {
            val currentPage = params.key ?: 1
            val response = appUseCases.getMoviesByCategoryUseCase.invoke(typeList, currentPage, 16)

            Log.d("MoviePagingSource", "Page = $currentPage, Size = ${params.loadSize}, Result = ${response.data?.size}")

            val movies = response.data ?: emptyList()



            LoadResult.Page(
                data = movies,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (movies.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieByCategory>): Int? {
        return state.anchorPosition

    }
}
