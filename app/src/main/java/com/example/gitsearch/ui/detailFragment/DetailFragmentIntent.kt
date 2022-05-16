package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.remote.model.Item

sealed class DetailFragmentIntent {
    data class GetAllById(val modelId: Int, val ownerId: Int) : DetailFragmentIntent()
    data class GetModel(val mainModel: Item) : DetailFragmentIntent()
}