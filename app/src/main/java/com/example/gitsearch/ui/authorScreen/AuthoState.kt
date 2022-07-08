package com.example.gitsearch.ui.authorScreen

import com.example.gitsearch.data.local.model.OwnerLocalModel

sealed class AuthoState {
    object Idle : AuthoState()
    object Loading : AuthoState()
    data class DataLoaded(val ownerModel: OwnerLocalModel) : AuthoState()
    data class Error(val error: String?) : AuthoState()
}