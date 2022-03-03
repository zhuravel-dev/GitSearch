package com.example.gitsearch.ui.detailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import androidx.compose.ui.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel
import com.example.gitsearch.ui.compose.DEFAULT_AVATAR_IMAGE
import com.example.gitsearch.ui.compose.loadPicture
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

const val IMAGE_HEIGHT = 260

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val detailFragmentViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val modelId by lazy { args.myModelId }
    private val ownerId by lazy { args.myOwnerId }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @OptIn(InternalCoroutinesApi::class)
    private fun observeViewModel() {
        lifecycleScope.launch {
            detailFragmentViewModel.state.collect {
                when (it) {
                    is DetailFragmentState.Idle -> {}
                    is DetailFragmentState.Loading -> {
                        //viewBinding?.progressBar?.visibility = View.VISIBLE
                    }
                    is DetailFragmentState.DataLoadedAll -> {
                        //viewBinding?.progressBar?.visibility = View.GONE
                        // showModelInformation(it.model, it.ownerModel)
                        (view as? FrameLayout)?.run {
                            removeAllViews()
                            addView(ComposeView(requireContext()).apply {
                                setContent {
                                    MyDetailScreen(it.model, it.ownerModel)
                                }
                            })
                        }
                    }
                    is DetailFragmentState.Error -> {
                        //viewBinding?.progressBar?.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeViewModel()
        lifecycleScope.launch {
            detailFragmentViewModel.onIntent(DetailFragmentIntent.GetAllById(modelId, ownerId))
        }
        return FrameLayout(requireContext())
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyDetailScreen(
    model: ItemLocalModel,
    ownerModel: OwnerLocalModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail info") },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = {

                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            stickyHeader {
                Box(
                    Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .height(300.dp)
                )
                val image = loadPicture(
                    url = ownerModel.avatar_url,
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
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = ownerModel.login,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                        val nameOfRepository = "${model.name} repository"
                        Text(
                            text = nameOfRepository,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.Start),
                            style = MaterialTheme.typography.h3
                        )
                        model.description?.let { description ->
                            Text(
                                text = description,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h3
                            )
                            model.language?.let { language ->
                                Text(
                                    text = language,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.Start),
                                    style = MaterialTheme.typography.h3
                                )
                            }
                            val topics = model.topics.toString()
                                .substring(1, model.topics.toString().length - 1)
                            Text(
                                text = topics,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h3
                            )
                            val watchers = "${model.watchers.toString()} watchers"
                            Text(
                                text = watchers,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.Start),
                                style = MaterialTheme.typography.h3
                            )
                        }
                    }
                }
            }
        }
    }
}