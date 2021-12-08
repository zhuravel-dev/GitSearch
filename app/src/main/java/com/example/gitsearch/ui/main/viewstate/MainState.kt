package com.example.gitsearch.ui.main.viewstate

import com.example.gitsearch.data.model.Item

sealed class MainState{

    object Idle : MainState()
    object Loading : MainState()
    data class Repository(val repo: List<Item>) : MainState()
    data class Error(val error: String?) : MainState()

}
