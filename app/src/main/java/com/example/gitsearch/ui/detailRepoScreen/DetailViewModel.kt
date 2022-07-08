package com.example.gitsearch.ui.detailRepoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val _state = MutableStateFlow<DetailState>(DetailState.Idle)
    val state: StateFlow<DetailState>
        get() = _state

    fun onIntent(event: DetailIntent) {
        when (event) {
            is DetailIntent.GetModelById -> getAllById(event.modelId)
        }
    }

    private fun getAllById(modelId: Int) {
        viewModelScope.launch {
            _state.value = DetailState.Loading
            val model = repository.getModelById(modelId)
            model.owner = repository.getOneOwnerById(model.ownerId)
            delay(600)
            _state.value = DetailState.DataLoadedAll(model)
        }
    }
}