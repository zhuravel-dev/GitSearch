package com.example.gitsearch.ui.firstFragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentFirstSortingByBinding
import com.example.gitsearch.ui.activities.MainSharedViewModel
import com.example.gitsearch.ui.activities.MainState
import com.example.gitsearch.ui.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FirstFragmentSortingByUpdate : Fragment(R.layout.fragment_first_sorting_by) {

    private val viewBinding: FragmentFirstSortingByBinding? by viewBinding(FragmentFirstSortingByBinding::bind)
    private val pagingAdapter by lazy { FirstFragmentAdapter() }
    private val mainSharedViewModel: MainSharedViewModel by activityViewModels()

    private fun initAdapter() {
        pagingAdapter.onItemClick = {
            val action =
                FirstFragmentSortingByStarsDirections.actionFragmentFirstToFragmentDetail(it.id, it.ownerId)
            findNavController().navigate(action)
        }
        viewBinding?.recyclerView?.adapter = pagingAdapter.withLoadStateHeaderAndFooter(
            header = FirstFragmentLoaderStateAdapter(),
            footer = FirstFragmentLoaderStateAdapter()
        )
        pagingAdapter.addLoadStateListener { state ->
            viewBinding?.run {
                recyclerView.isVisible = state.refresh != LoadState.Loading
                progressBar.isVisible = state.refresh == LoadState.Loading
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        initAdapter()
    }

    private fun setupUI() = viewBinding?.run {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager)
                        .orientation
                )
            )
        }
        recyclerView.adapter = pagingAdapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainSharedViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {
                        viewBinding?.tvWelcomeText?.visibility = View.VISIBLE
                    }
                    is MainState.Loading -> {
                        viewBinding?.tvWelcomeText?.visibility = View.GONE
                        viewBinding?.progressBar?.visibility = View.VISIBLE
                    }
                    is MainState.DataLoaded -> {
                        viewBinding?.tvWelcomeText?.visibility = View.GONE
                        viewBinding?.progressBar?.visibility = View.GONE
                        viewBinding?.recyclerView?.visibility = View.VISIBLE
                        pagingAdapter.submitData(it.data)
                    }
                    is MainState.Error -> {
                        viewBinding?.tvWelcomeText?.visibility = View.GONE
                        viewBinding?.progressBar?.visibility = View.GONE
                    }
                }
            }
        }
    }
}