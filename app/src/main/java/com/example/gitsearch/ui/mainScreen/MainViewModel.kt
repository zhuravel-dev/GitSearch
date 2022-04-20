package com.example.gitsearch.ui.mainScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.remote.model.ItemsResponse
import com.example.gitsearch.domain.repository.MainRepository
import com.example.gitsearch.ui.pager.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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


    /*private val _pagingData = MutableStateFlow<PagingData<ItemLocalModel>>()
    val pagingData: StateFlow<PagingData<ItemLocalModel>>
        get() = _pagingData*/

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


    fun onIntent(event: MainIntent) {
        when (event) {
            is MainIntent.SearchGitListSortedByStars -> searchListSortedByStars(event.q)
            //is MainIntent.SearchGitListSortedByUpdate -> searchListSortedByUpdate(event.q)
        }
    }

    private fun searchListSortedByStars(q: String? = null) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
            _state.value = MainState.Error(exception.message.orEmpty())
        }
        viewModelScope.launch(handler) {
            _state.value = MainState.Loading
            val data = repository.getResponse(q)
            _state.value = MainState.DataLoaded(data)
        }
    }
}