package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.local.model.ItemLocalModel

sealed class DetailFragmentIntent {
    data class GetAllById(val modelId: Int, val ownerId: Int) : DetailFragmentIntent()
    data class GetModel(val mainModel: ItemLocalModel) : DetailFragmentIntent()
}