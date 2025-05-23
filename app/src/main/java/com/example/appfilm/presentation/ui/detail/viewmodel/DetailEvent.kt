package com.example.appfilm.presentation.ui.detail.viewmodel

import com.example.appfilm.domain.model.detail_movie.MovieDetail

sealed class DetailEvent {
    data class ReTry(val slug: String) : DetailEvent()
    data class GetDetail(val slug: String) : DetailEvent()
    data class AddFavouriteMovie(val movieDetail: MovieDetail) : DetailEvent()
    data class CheckFavouriteMovie(val movieID: String) : DetailEvent()
    data class DeleteFavouriteMovie(val movieId: String) : DetailEvent()
}