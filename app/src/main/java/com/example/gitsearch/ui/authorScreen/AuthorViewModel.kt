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

    private val _state = MutableStateFlow<AuthorFragmentState>(AuthorFragmentState.Idle)
    val state: StateFlow<AuthorFragmentState>
        get() = _state

    fun onIntent(event: AuthorFragmentIntent) {
        when (event) {
            is AuthorFragmentIntent.GetOwnerById -> getAllById(event.ownerId)
        }
    }

    private fun getAllById(ownerId: Int) {
        viewModelScope.launch {
            _state.value = AuthorFragmentState.Loading
            val owner = repository.getOneOwnerById(ownerId)
            _state.value = AuthorFragmentState.DataLoaded(owner)
        }
    }
}