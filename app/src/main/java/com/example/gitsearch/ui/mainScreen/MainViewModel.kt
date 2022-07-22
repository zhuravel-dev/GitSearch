package com.example.gitsearch.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    fun onIntent(event: MainIntent) {
        when (event) {
            is MainIntent.SearchGitList -> searchListSorted(event.q)
        }
    }

    private fun searchListSorted(q: String) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
            _state.value = MainState.Error(exception.message.orEmpty())
        }
        viewModelScope.launch(handler) {
            _state.value = MainState.Loading
            val dataByStars = repository.getDataFromMediatorSortedByStars(q)
            val dataByUpdate = repository.getDataFromMediatorSortedByUpdate(q)
            _state.value = MainState.DataLoaded(dataByStars, dataByUpdate)
        }
    }
}