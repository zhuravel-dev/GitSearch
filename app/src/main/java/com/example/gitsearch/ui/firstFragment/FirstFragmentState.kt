package com.example.gitsearch.ui.firstFragment

import androidx.paging.PagingData
import com.example.gitsearch.data.model.Item
import kotlinx.coroutines.flow.Flow

sealed class FirstFragmentState {

    object Idle : FirstFragmentState()
    object Loading : FirstFragmentState()
    data class DataLoaded(val repo: Flow<PagingData<Item>>) : FirstFragmentState()
    data class Error(val error: String?) : FirstFragmentState()

}