package com.example.gitsearch.ui.pager

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
class PagerSharedViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MainPagerState>(MainPagerState.Idle)
    val pagerState: StateFlow<MainPagerState>
        get() = _state

    @ExperimentalPagingApi
    fun onIntent(event: PagerIntent) {
        when (event) {
            is PagerIntent.SearchGitListSortedByStars -> searchListSortedByStars(event.q)
            is PagerIntent.SearchGitListSortedByUpdate -> searchListSortedByUpdate(event.q)
        }
    }

    @ExperimentalPagingApi
    private fun searchListSortedByStars(q: String) {
        viewModelScope.launch {
            _state.value = MainPagerState.Loading
            repository.getDataFromMediatorSortedByStars(q).cachedIn(viewModelScope).collectLatest {
                _state.value = MainPagerState.DataLoaded(it)
            }
        }
    }

    private fun searchListSortedByUpdate(q: String) {
        viewModelScope.launch {
            _state.value = MainPagerState.Loading
            repository.getDataFromMediatorSortedByUpdate(q).cachedIn(viewModelScope).collectLatest {
                _state.value = MainPagerState.DataLoaded(it)
            }
        }
    }
}