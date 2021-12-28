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

    private val _stateFirst = MutableStateFlow<FirstFragmentState>(FirstFragmentState.Idle)
    val stateFirst: StateFlow<FirstFragmentState>
        get() = _stateFirst

    fun onIntent(event: FirstFragmentIntent) {
        when (event) {
            is FirstFragmentIntent.SearchGitList -> fetchList(event.q)
            is FirstFragmentIntent.SetSelectedRepositoryId -> setSelectedRepositoryId(event.id)
        }
    }

    private fun fetchList(q: String) {
        viewModelScope.launch {
            _stateFirst.value = FirstFragmentState.Loading
            _stateFirst.value = try {
                FirstFragmentState.DataLoaded(repository.getRepo(q))
            } catch (e: Exception) {
                FirstFragmentState.Error(e.localizedMessage)
            }
        }
    }

    private fun setSelectedRepositoryId(id: Int) = repository.setSelectedId(id)
}