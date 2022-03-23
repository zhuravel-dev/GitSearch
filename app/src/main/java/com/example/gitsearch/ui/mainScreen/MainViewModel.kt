package com.example.gitsearch.ui.mainScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.gitsearch.domain.repository.MainRepository
import com.example.gitsearch.ui.pager.SearchState
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
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    private val _searchState: MutableState<SearchState> = mutableStateOf(SearchState.CLOSED)
    val searchState: State<SearchState> = _searchState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchState(newVal: SearchState) {
        _searchState.value = newVal
    }

    fun updateSearchTextState(newVal: String) {
        _searchTextState.value = newVal
    }

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