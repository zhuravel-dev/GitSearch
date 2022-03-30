package com.example.gitsearch.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontStyle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay

@OptIn(InternalCoroutinesApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class FragmentSortingByStars : Fragment() {

    private val pagingAdapter by lazy { FirstFragmentAdapter() }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SetupUI()
            }
        }
    }


    @Composable
    private fun LaunchFragment(q: String, viewModel: MainViewModel) {

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
            //is MainState.DataLoaded -> SetupUI()
            is MainState.Error -> ErrorDialog()
        }
    }

    @Composable
    fun SetupUI() {
        Text("Hello World", fontStyle = FontStyle.Italic)
    }
}


/* private fun initAdapter() {
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
 }*/

/* private fun setupUI() = viewBinding?.run {
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
 }*/

/* private fun observeViewModel() {
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
 }*/
