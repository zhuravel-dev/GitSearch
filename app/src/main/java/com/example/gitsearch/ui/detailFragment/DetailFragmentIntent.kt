package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.local.model.ItemLocalModel

sealed class DetailFragmentIntent {

    data class GetDetailInfo(val detailData : ItemLocalModel) : DetailFragmentIntent()
    data class GetModelById(val id: Int) : DetailFragmentIntent()
}