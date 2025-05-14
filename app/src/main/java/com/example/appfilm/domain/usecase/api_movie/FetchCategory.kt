package com.example.appfilm.domain.usecase.api_movie

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Category
import com.example.appfilm.domain.repository.IApiMovie
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FetchCategory @Inject constructor(
    private val movieRepository : IApiMovie
) {
    suspend fun fetchCategory() : Resource<List<Category>>{
        return movieRepository.fetchCategory()
    }

}