package com.example.gitsearch.ui.mainScreen

import MainFragmentWithPagerUI
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import com.example.gitsearch.data.local.model.ItemLocalModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainFragmentWithPager() : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    val model:  ItemLocalModel = TODO()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(
                        topBar = {
                            Column() {
                                MyAppBar(viewModel = mainViewModel)
                                Tabs()
                            }
                        }
                    ) {
                        MainFragmentWithPagerUI(viewModel = mainViewModel, onClick = {} )
                    }
                }
            }
        }
    }


    private fun action(model: ItemLocalModel){
        findNavController().navigate(MainFragmentWithPagerDirections.actionToDetailFragment(model))
    }
}