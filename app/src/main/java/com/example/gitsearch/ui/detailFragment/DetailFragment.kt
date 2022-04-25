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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.DEFAULT_AVATAR_IMAGE
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.loadPicture
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay

const val IMAGE_HEIGHT = 360

@OptIn(InternalCoroutinesApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class DetailFragment : Fragment() {

    private val detailFragmentViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val mainModel by lazy { args.mainModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme() {
                    launchDetailScreen(model = mainModel, viewModel = detailFragmentViewModel)
                }
            }
        }
    }

    @Composable
    private fun launchDetailScreen(model: ItemLocalModel, viewModel: DetailViewModel) {

        val viewState by viewModel.state.collectAsState()

        LaunchedEffect(true) {
            delay(1000)
            viewModel.onIntent(DetailFragmentIntent.GetModel(mainModel))
        }

        when (viewState) {
            is DetailFragmentState.Idle -> {
                CircularProgress(modifier = Modifier)
            }
            is DetailFragmentState.Loading -> {}
            is DetailFragmentState.DataLoadedMainModel -> setupUI(model = model)
            is DetailFragmentState.Error -> ErrorDialog(modifier = Modifier)
            else -> {}
        }
    }

    @Composable
    private fun setupUI(model: ItemLocalModel) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
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

            Box(
                Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(360.dp)
                    .constrainAs(image) {
                        top.linkTo(topBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                val image = loadPicture(
                    url = model.owner!!.avatar_url,
                    defaultImage = DEFAULT_AVATAR_IMAGE
                ).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Owner`s avatar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IMAGE_HEIGHT.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .constrainAs(textColumn) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = model.owner!!.login,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h5
                )
                val nameOfRepository = "${model.name} repository"
                Text(
                    text = nameOfRepository,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Gray,
                    style = MaterialTheme.typography.h5
                )
                model.description?.let { description ->
                    Text(
                        text = description,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        color = Color.Gray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    model.language?.let { language ->
                        Text(
                            text = language,
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
                        style = MaterialTheme.typography.subtitle1
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
}