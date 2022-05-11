package com.example.gitsearch.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi


@OptIn(InternalCoroutinesApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class DetailFragment : Fragment() {

    private val detailFragmentViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val model by lazy { args.mainModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme() {
                    setupUI(model = model)
                }
            }
        }
    }

   /* @Composable
    private fun launchDetailScreen(model: Item, viewModel: DetailViewModel) {

        val viewState by viewModel.state.collectAsState()

        LaunchedEffect(true) {
            delay(1000)
            viewModel.onIntent(DetailFragmentIntent.GetModel(model))
        }

        when (viewState) {
            is DetailFragmentState.Idle -> {}
            is DetailFragmentState.Loading -> {
                CircularProgress(modifier = Modifier)
            }
            is DetailFragmentState.DataLoadedMainModel -> setupUI(model = model)
            is DetailFragmentState.Error -> ErrorDialog(modifier = Modifier)
            else -> {}
        }
    }*/


    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun setupUI(model: ItemLocalModel) {

        ConstraintLayout(
            modifier = Modifier
                .background(Color.White)
        )
        {
            val (topBar, image, textColumn) = createRefs()

            TopAppBar(
                modifier = Modifier
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                backgroundColor = MaterialTheme.colors.primary,
                title = {
                    Text(
                        text = "Detail information",
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h6
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { findNavController().popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )

            val painter = rememberImagePainter(data = model.owner?.avatar_url)

            Image(
                painter = painter,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 8.dp)
                    //.background(Color.LightGray)
                    .size(440.dp, 360.dp)
                    .constrainAs(image) {
                        top.linkTo(topBar.bottom)
                        start.linkTo(parent.start)
                        bottom.linkTo(textColumn.top)
                        end.linkTo(parent.end)
                    },
                alignment = Alignment.Center
            )

            Column(
                modifier = Modifier
                    .constrainAs(textColumn) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(12.dp, 0.dp, 12.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                model.owner?.let {
                    Text(
                        text = it.login,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h5,
                        maxLines = 1
                    )
                }
                val nameOfRepository = "${model.name} repository"
                Text(
                    text = nameOfRepository,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Gray,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1
                )
                model.description?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start),
                        color = Color.Gray,
                        style = MaterialTheme.typography.subtitle1,
                        maxLines = 2
                    )
                }
                model.language?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        color = Color.Gray,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                val topics = model.topics.toString()
                    .substring(1, model.topics.toString().length - 1)
                Text(
                    text = topics,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Gray,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1
                )
                val watchers = "${model.watchers} watchers"
                Text(
                    text = watchers,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Gray,
                    style = MaterialTheme.typography.subtitle1
                )
            }
        }
    }
}