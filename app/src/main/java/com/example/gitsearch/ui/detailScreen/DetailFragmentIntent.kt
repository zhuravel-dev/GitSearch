package com.example.gitsearch.ui.detailScreen

sealed class DetailFragmentIntent {
    data class GetAllById(val modelId: Int, val ownerId: Int) : DetailFragmentIntent()
}