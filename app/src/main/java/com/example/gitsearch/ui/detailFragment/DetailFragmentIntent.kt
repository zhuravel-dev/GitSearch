package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.model.Item

sealed class DetailFragmentIntent {

    data class GetDetailInfo(val model : Item) : DetailFragmentIntent()
}