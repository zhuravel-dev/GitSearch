package com.example.gitsearch.ui.firstFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class FirstFragmentViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<FirstFragmentState>(FirstFragmentState.Idle)
    val state: StateFlow<FirstFragmentState>
        get() = _state


    fun onIntent(event: FirstFragmentIntent) {
        when (event) {
            is FirstFragmentIntent.FetchGitList -> fetchList()
            is FirstFragmentIntent.SearchGitList -> fetchList(event.query)
        }
    }

    private fun fetchList(query: String = "") {
        viewModelScope.launch {
            _state.value = FirstFragmentState.Loading
            _state.value = try {
                FirstFragmentState.DataLoaded(repository.getRepo(query))
            } catch (e: Exception) {
                FirstFragmentState.Error(e.localizedMessage)
            }
        }
    }
}