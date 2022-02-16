package com.example.gitsearch.ui.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.gitsearch.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltViewModel
class MainSharedViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    @ExperimentalPagingApi
    fun onIntent(event: MainIntent) {
        when (event) {
            is MainIntent.SearchGitListSortedByStars -> searchListSortedByStars(event.q)
            is MainIntent.SearchGitListSortedByUpdate -> searchListSortedByUpdate(event.q)
        }
    }

    @ExperimentalPagingApi
    private fun searchListSortedByStars(q: String) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            repository.getDataFromMediatorSortedByStars(q).cachedIn(viewModelScope).collectLatest {
                _state.value = MainState.DataLoaded(it)
            }
        }
    }

    private fun searchListSortedByUpdate(q: String) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            repository.getDataFromMediatorSortedByUpdate(q).cachedIn(viewModelScope).collectLatest {
                _state.value = MainState.DataLoaded(it)
            }
        }
    }
}