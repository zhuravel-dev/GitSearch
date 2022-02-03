package com.example.gitsearch.ui.detailFragment

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

    private val _state = MutableStateFlow<DetailFragmentState>(DetailFragmentState.Idle)
    val state: StateFlow<DetailFragmentState>
        get() = _state

    fun onIntent(event: DetailFragmentIntent) {
        when (event) {
            is DetailFragmentIntent.GetDetailInfo -> getDetailInfo(event.detailData)
            is DetailFragmentIntent.GetModelById -> getModelById(event.id)
            is DetailFragmentIntent.GetOwnerById -> getOwnerById(event.id)
        }
    }

    private fun getDetailInfo(detailData: ItemLocalModel) {
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            _state.value = try {
                DetailFragmentState.DataLoaded(repository.getDetailInfo(detailData))
            } catch (e: Exception) {
                DetailFragmentState.Error(e.localizedMessage)
            }
        }
    }

    private fun getModelById(id: Int){
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            _state.value = try {
                DetailFragmentState.DataLoaded(repository.getModelById(id))
            } catch (e: Exception) {
                DetailFragmentState.Error(e.localizedMessage)
            }
        }
    }

    private fun getOwnerById(id: Int){
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            _state.value = try {
                DetailFragmentState.DataLoadedOwner(repository.getOneOwnerById(id))
            } catch (e: Exception) {
                DetailFragmentState.Error(e.localizedMessage)
            }
        }
    }
}