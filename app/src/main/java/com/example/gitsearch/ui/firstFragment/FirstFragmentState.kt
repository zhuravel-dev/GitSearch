package com.example.gitsearch.ui.firstFragment

import com.example.gitsearch.data.model.Item

sealed class FirstFragmentState {

    object Idle : FirstFragmentState()
    object Loading : FirstFragmentState()
    data class DataLoaded(val repo: List<Item>) : FirstFragmentState()
    data class Error(val error: String?) : FirstFragmentState()

}