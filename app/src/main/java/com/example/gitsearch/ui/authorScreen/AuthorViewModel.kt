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

    private val _state = MutableStateFlow<AuthoState>(AuthoState.Idle)
    val state: StateFlow<AuthoState>
        get() = _state

    fun onIntent(event: AuthorIntent) {
        when (event) {
            is AuthorIntent.GetOwnerById -> getAllById(event.ownerId)
        }
    }

    private fun getAllById(ownerId: Int) {
        viewModelScope.launch {
            _state.value = AuthoState.Loading
            val owner = repository.getOneOwnerById(ownerId)
            _state.value = AuthoState.DataLoaded(owner)
        }
    }
}