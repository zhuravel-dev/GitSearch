/*
package com.example.gitsearch.ui.detailRepoScreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.example.gitsearch.ui.detailRepoScreen.ui.DetailRepoUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class DetailRepoFragment : Fragment() {

    private val args: DetailRepoFragmentArgs by navArgs()
    private val model by lazy { args.mainModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    DetailRepoUI(
                        model = model,
                        onClickBack = { findNavController().popBackStack() }
                    ) {
                        findNavController().navigate(
                            DetailRepoFragmentDirections.actionToAuthorFragment(model)
                        )
                    }
                }
            }
        }
    }
}*/
