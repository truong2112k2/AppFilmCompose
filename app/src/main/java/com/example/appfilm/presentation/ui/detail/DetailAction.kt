package com.example.appfilm.presentation.ui.detail

sealed class DetailAction {
    data class ReTry(val slug: String) : DetailAction()
    data class GetDetail(val slug: String) : DetailAction()
}