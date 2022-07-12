package com.example.gitsearch.ui.authorScreen

import com.example.gitsearch.data.local.model.OwnerLocalModel

sealed class AuthorState {
    object Idle : AuthorState()
    object Loading : AuthorState()
    data class DataLoaded(val ownerModel: OwnerLocalModel) : AuthorState()
    data class Error(val error: String?) : AuthorState()
}