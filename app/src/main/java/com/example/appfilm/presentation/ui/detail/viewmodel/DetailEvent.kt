package com.example.appfilm.presentation.ui.detail.viewmodel

sealed class DetailEvent {
    data class ReTry(val slug: String) : DetailEvent()
    data class GetDetail(val slug: String) : DetailEvent()
}