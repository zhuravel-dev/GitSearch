package com.example.gitsearch.ui.firstFragment

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


@ExperimentalCoroutinesApi
@HiltViewModel
class FirstFragmentViewModel @ExperimentalPagingApi
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FirstFragmentState>(FirstFragmentState.Idle)
    val state: StateFlow<FirstFragmentState>
        get() = _state

    @ExperimentalPagingApi
    fun onIntent(event: FirstFragmentIntent) {
        when (event) {
            is FirstFragmentIntent.SearchGitList -> searchList(event.q)
            is FirstFragmentIntent.SetSelectedRepositoryId -> setSelectedRepositoryId(event.id)
        }
    }

    @ExperimentalPagingApi
    private fun searchList(q: String) {
        viewModelScope.launch {
            _state.value = FirstFragmentState.Loading
            repository.getRepoFromNetwork(q).cachedIn(viewModelScope).collectLatest {
                _state.value = FirstFragmentState.DataLoaded(it)
            }
        }
    }

    @ExperimentalPagingApi
    private fun setSelectedRepositoryId(id: Int) = repository.setSelectedId(id)
}