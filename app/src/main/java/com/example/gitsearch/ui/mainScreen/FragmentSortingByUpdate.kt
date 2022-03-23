/*
package com.example.gitsearch.ui.mainScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentFirstSortingByUpdateBinding
import com.example.gitsearch.ui.pager.MainPagerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FragmentSortingByUpdate : Fragment(R.layout.fragment_first_sorting_by_update) {

    private val viewBinding: FragmentFirstSortingByUpdateBinding? by viewBinding(
        FragmentFirstSortingByUpdateBinding::bind
    )
    private val pagingAdapter by lazy { FirstFragmentAdapter() }
    private val MainViewModel: MainViewModel by activityViewModels()

    private fun initAdapter() {
        pagingAdapter.onItemClick = {
            findNavController().navigate(
                MainPagerFragmentDirections.actionToDetailFragment(it))
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
            MainViewModel.state.collect {
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
}*/
