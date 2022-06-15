package com.example.gitsearch.ui.authorScreen

import com.example.gitsearch.data.local.model.OwnerLocalModel

sealed class AuthorFragmentState {
    object Idle : AuthorFragmentState()
    object Loading : AuthorFragmentState()
    data class DataLoaded(val ownerModel: OwnerLocalModel) : AuthorFragmentState()
    data class Error(val error: String?) : AuthorFragmentState()
}