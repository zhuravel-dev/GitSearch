/*
package com.example.gitsearch.ui.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.domain.repository.MainRepository
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
            is DetailFragmentIntent.GetAllById -> getAllById(event.modelId, event.ownerId)
        }
    }

    private fun getAllById(modelId: Int, ownerId: Int) {
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            val model = repository.getModelById(modelId)
            val owner = model.owner
            _state.value = DetailFragmentState.DataLoadedAll(model, owner!!)
        }
    }
}*/
