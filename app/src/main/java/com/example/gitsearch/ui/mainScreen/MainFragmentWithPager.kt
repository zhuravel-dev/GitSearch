package com.example.gitsearch.ui.mainScreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.WelcomeText
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragmentWithPager : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme {
                    LaunchMainScreen(viewModel = mainViewModel)
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun LaunchMainScreen(viewModel: MainViewModel) {

        val resultState by viewModel.state.collectAsState()
        val pages = remember { listOf("Sorting by stars", "Sorting by update") }
        val pagerState = rememberPagerState(pageCount = pages.size)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val (topAppBar, pager, welcomeText, progress, error) = createRefs()

            MyAppBar(
                viewModel = mainViewModel,
                topAppBar
            )

            when (resultState) {
                is MainState.Idle -> {
                    WelcomeText(
                        welcomeText,
                        topAppBar
                    )
                }

                is MainState.Loading -> {
                    CircularProgress(progress)
                }

                is MainState.DataLoaded -> {
                    val listStars =
                        (resultState as MainState.DataLoaded).dataSortedByStars.collectAsLazyPagingItems()
                    val listUpdate =
                        (resultState as MainState.DataLoaded).dataSortedByUpdate.collectAsLazyPagingItems()

                    this@ConstraintLayout.SetupPager(
                        topAppBar,
                        pager,
                        pagerState = pagerState,
                        userListByStars = listStars,
                        userListByUpdate = listUpdate,
                        onClick = {
                            findNavController().navigate(
                                MainFragmentWithPagerDirections.actionToDetailFragment(it)
                            )
                        })
                }
                is MainState.Error -> ErrorDialog(error)
            }
        }
    }
}