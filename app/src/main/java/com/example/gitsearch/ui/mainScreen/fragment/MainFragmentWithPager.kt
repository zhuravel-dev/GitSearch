package com.example.gitsearch.ui.mainScreen.fragment

import MainFragmentWithPagerUI
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.example.gitsearch.ui.mainScreen.MainViewModel
import com.example.gitsearch.ui.mainScreen.ui.MyAppBar
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class MainFragmentWithPager() : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            topBar = {
                                Column {
                                    MyAppBar(viewModel = mainViewModel)
                                }
                            }
                        ) {
                            MainFragmentWithPagerUI(
                                viewModel = mainViewModel,
                                onClick = {
                                    findNavController().navigate(
                                        MainFragmentWithPagerDirections.actionToDetailFragment(it)
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}