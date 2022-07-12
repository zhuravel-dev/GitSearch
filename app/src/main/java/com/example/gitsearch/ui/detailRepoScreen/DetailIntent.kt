package com.example.gitsearch.ui.detailRepoScreen

sealed class DetailIntent {
    data class GetModelById(val modelId: Int) : DetailIntent()
}