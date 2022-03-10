package com.example.gitsearch.ui.detailFragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val loading = mutableStateOf(false)

    private val _state = MutableStateFlow<DetailFragmentState>(DetailFragmentState.Idle)
    val state: StateFlow<DetailFragmentState>
        get() = _state

    fun onIntent(event: DetailFragmentIntent) {
        when (event) {
            is DetailFragmentIntent.GetModel -> getModel(event.mainModel)
            //is DetailFragmentIntent.GetAllById -> getAllById(event.modelId, event.ownerId)
        }
    }

    private fun getModel(model: ItemLocalModel) {
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            _state.value = DetailFragmentState.DataLoadedMainModel(model)
        }
    }

    private fun getAllById(modelId: Int, ownerId: Int) {
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            val model = repository.getModelById(modelId)
            val owner = repository.getOneOwnerById(ownerId)
            _state.value = DetailFragmentState.DataLoadedAll(model, owner)
        }
    }
}