package com.example.gitsearch.ui.detailFragment

sealed class DetailFragmentIntent {

    data class GetDetailInfo(val id : Int) : DetailFragmentIntent()
}