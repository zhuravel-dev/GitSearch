package com.example.gitsearch.ui.authorScreen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.authorScreen.AuthorFragmentIntent
import com.example.gitsearch.ui.authorScreen.AuthorFragmentState
import com.example.gitsearch.ui.authorScreen.AuthorViewModel
import com.example.gitsearch.ui.authorScreen.ui.AuthorFragmentUI
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class AuthorFragment : Fragment() {

    private val authorFragmentViewModel: AuthorViewModel by viewModels()
    private val args: AuthorFragmentArgs by navArgs()
    val model by lazy { args.mainModel }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = isSystemInDarkTheme()) {
                    SetupAuthorScreen(model, authorFragmentViewModel)
                }
            }
        }
    }

    @Composable
    fun SetupAuthorScreen(model: ItemLocalModel, viewModel: AuthorViewModel) {
        val viewState by viewModel.state.collectAsState()

        LaunchedEffect(true) {
            delay(1000)
            viewModel.onIntent(AuthorFragmentIntent.GetOwnerById(model.ownerId))
        }

        when (viewState) {
            is AuthorFragmentState.Idle -> {}
            is AuthorFragmentState.Loading -> {
                //CircularProgress(modifier = Modifier)
            }
            is AuthorFragmentState.DataLoaded -> AuthorFragmentUI(model = model, onClick = { findNavController().popBackStack() })
            is AuthorFragmentState.Error -> {}//ErrorDialog(modifier = Modifier)
            else -> {}
        }
    }


}

