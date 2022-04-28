package com.example.gitsearch.ui.detailFragment

import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.data.remote.model.Owner

sealed class DetailFragmentState {

    object Idle : DetailFragmentState()
    object Loading : DetailFragmentState()
    data class DataLoadedAll(val model: Item, val ownerModel: Owner) : DetailFragmentState()
    data class DataLoadedMainModel(val model: Item) : DetailFragmentState()
    data class Error(val error: String?) : DetailFragmentState()

}