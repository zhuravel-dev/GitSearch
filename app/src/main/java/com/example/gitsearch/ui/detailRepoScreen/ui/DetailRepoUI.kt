package com.example.gitsearch.ui.detailRepoScreen.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdsClick
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.theme.Black
import com.example.gitsearch.ui.compose.theme.White
import com.example.gitsearch.ui.detailRepoScreen.DetailFragmentIntent
import com.example.gitsearch.ui.detailRepoScreen.DetailFragmentState
import com.example.gitsearch.ui.detailScreen.DetailViewModel
import com.example.gitsearch.ui.extensions.shareRepoInfo

@OptIn(ExperimentalCoilApi::class, androidx.paging.ExperimentalPagingApi::class)
@Composable
fun DetailRepoUI(
    id: Int,
    navController: NavController,
) {
    val context = LocalContext.current

    val viewModel = hiltViewModel<DetailViewModel>()
    viewModel.onIntent(DetailFragmentIntent.GetModelById(id))

    val state by viewModel.state.collectAsState()

    when (state) {
        DetailFragmentState.Idle -> {}
        DetailFragmentState.Loading -> {}
        is DetailFragmentState.DataLoadedAll -> {
            DetailRepoUI(model = (state as DetailFragmentState.DataLoadedAll).model,
                navController = navController,
                context = context)
        }
        is DetailFragmentState.Error -> {}
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailRepoUI(
    model: ItemLocalModel,
    navController: NavController,
    context: Context
) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    )
    {
        val (topBar, image, login, nameOfRepository,
            description, language, topics, watchers) = createRefs()

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
                    text = "Repository`s detail",
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h6
                )
            },
           navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    context.startActivity(shareRepoInfo(model))
                })
                {
                    Icon(Icons.Default.Share, "share repository`s info", tint = White)
                }
            }
        )

        val painter = rememberImagePainter(data = model.owner?.avatar_url)
        Image(
            painter = painter,
            contentDescription = "User Avatar",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 8.dp)
                .size(440.dp, 360.dp)
                .constrainAs(image) {
                    top.linkTo(topBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            alignment = Alignment.Center
        )

        Row(modifier = Modifier
            .constrainAs(login) {
                top.linkTo(image.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        {
            model.owner?.let {
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                        .clickable {
                            navController.navigate("detail_author_screen/id=${it.id}")
                        },
                    text = it.login,
                    color = Black,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
            Icon(Icons.Default.AdsClick, "click")
        }

        Text(
            text = "${model.name} repository",
            modifier = Modifier
                .constrainAs(nameOfRepository) {
                    top.linkTo(login.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            textAlign = TextAlign.Center)

        model.description?.let {
            Text(
                text = it,
                modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(nameOfRepository.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                textAlign = TextAlign.Center)
        }

        model.language?.let {
            Text(
                text = it,
                modifier = Modifier
                    .constrainAs(language) {
                        top.linkTo(description.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center)
        }

        Text(
            text = model.topics.toString()
                .substring(1, model.topics.toString().length - 1),
            modifier = Modifier
                .constrainAs(topics) {
                    top.linkTo(language.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            textAlign = TextAlign.Center)

        Text(
            text = "${model.watchers} watchers",
            modifier = Modifier
                .constrainAs(watchers) {
                    top.linkTo(topics.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center)
    }
}