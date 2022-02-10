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
            is MainIntent.SearchGitList -> searchList(event.q)
        }
    }

    @ExperimentalPagingApi
    private fun searchList(q: String) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            repository.getDataFromMediator(q).cachedIn(viewModelScope).collectLatest {
                _state.value = MainState.DataLoaded(it)
            }
        }
    }
}