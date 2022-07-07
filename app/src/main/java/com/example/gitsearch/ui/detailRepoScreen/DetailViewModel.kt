package com.example.gitsearch.ui.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.domain.repository.MainRepository
import com.example.gitsearch.ui.detailRepoScreen.DetailFragmentIntent
import com.example.gitsearch.ui.detailRepoScreen.DetailFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailFragmentState>(DetailFragmentState.Idle)
    val state: StateFlow<DetailFragmentState>
        get() = _state

    fun onIntent(event: DetailFragmentIntent) {
        when (event) {
            is DetailFragmentIntent.GetModelById -> getAllById(event.modelId)
        }
    }

    private fun getAllById(modelId: Int) {
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            val model = repository.getModelById(modelId)
            model.owner = repository.getOneOwnerById(model.ownerId)
            _state.value = DetailFragmentState.DataLoadedAll(model)
        }
    }
}
