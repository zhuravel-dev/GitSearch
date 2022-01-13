package com.example.gitsearch.ui.detailFragment

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
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailFragmentState>(DetailFragmentState.Idle)
    val state: StateFlow<DetailFragmentState>
        get() = _state

    fun onIntent(event: DetailFragmentIntent) {
        when (event) {
            is DetailFragmentIntent.GetDetailInfo -> getDetailInfo()
        }
    }

    private fun getDetailInfo() {
        viewModelScope.launch {
            _state.value = DetailFragmentState.Loading
            _state.value = try {
                val detailModel = repository.getDetailInfo()
                DetailFragmentState.DataLoaded(detailModel)
            } catch (e: Exception) {
                DetailFragmentState.Error(e.localizedMessage)
            }
        }
    }
}