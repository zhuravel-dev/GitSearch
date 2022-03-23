/*
package com.example.gitsearch.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.databinding.FragmentFirstSortingByStarsBinding
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FragmentSortingByStars : Fragment(R.layout.fragment_first_sorting_by_stars) {

    private val viewBinding: FragmentFirstSortingByStarsBinding? by viewBinding(
        FragmentFirstSortingByStarsBinding::bind
    )
    private val pagingAdapter by lazy { FirstFragmentAdapter() }
    private val MainViewModel: MainViewModel by activityViewModels()

   */
/* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        initAdapter()
    }*//*


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme() {
                   SetupUI()
                }
            }
        }
    }


    @Composable
    fun LaunchFragment(q: String, viewModel: MainViewModel) {

        val viewState by viewModel.state.collectAsState()

        LaunchedEffect(true) {
            delay(1000)
            viewModel.onIntent(MainIntent.SearchGitListSortedByStars(q))
        }

        when (viewState) {
            is MainState.Idle -> {
                CircularProgress()
            }
            is MainState.Loading -> {}
            // is MainState.DataLoaded -> setupUI(model = model)
            is MainState.Error -> ErrorDialog()
            else -> {}
        }
    }

    @Composable
    private fun SetupUI() {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        {


        }
        }



    private fun initAdapter() {
        pagingAdapter.onItemClick = {
            findNavController().navigate(MainFragmentWithPagerDirections.actionToDetailFragment(it))
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
}
*/
