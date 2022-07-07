package com.example.gitsearch.ui.detailRepoScreen

sealed class DetailFragmentIntent {
    data class GetModelById(val modelId: Int) : DetailFragmentIntent()
}