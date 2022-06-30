package com.example.gitsearch.ui.detailRepoScreen

sealed class DetailFragmentIntent {
    data class GetAllById(val modelId: Int) : DetailFragmentIntent()
}