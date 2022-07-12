package com.example.gitsearch.ui.authorScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AuthorViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AuthorState>(AuthorState.Idle)
    val state: StateFlow<AuthorState>
        get() = _state

    fun onIntent(event: AuthorIntent) {
        when (event) {
            is AuthorIntent.GetOwnerById -> getAllById(event.ownerId)
        }
    }

    private fun getAllById(ownerId: Int) {
        viewModelScope.launch {
            _state.value = AuthorState.Loading
            val owner = repository.getOneOwnerById(ownerId)
            _state.value = AuthorState.DataLoaded(owner)
        }
    }
}