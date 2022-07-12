package com.example.gitsearch.ui.authorScreen.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.gitsearch.data.local.model.OwnerLocalModel
import com.example.gitsearch.ui.authorScreen.AuthorIntent
import com.example.gitsearch.ui.authorScreen.AuthorState
import com.example.gitsearch.ui.authorScreen.AuthorViewModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.Black
import com.example.gitsearch.ui.compose.theme.White
import com.example.gitsearch.ui.extensions.shareAuthorInfo

@OptIn(ExperimentalCoilApi::class, androidx.paging.ExperimentalPagingApi::class)
@Composable
fun AuthorScreenUI(
    id: Int,
    navController: NavController
) {
    val context = LocalContext.current
    val authorViewModel = hiltViewModel<AuthorViewModel>()
    val state by authorViewModel.state.collectAsState()

    LaunchedEffect(true) {
        authorViewModel.onIntent(AuthorIntent.GetOwnerById(id))
    }

    when (state) {
        AuthorState.Idle -> {}
        AuthorState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgress(modifier = Modifier)
            }
        }
        is AuthorState.DataLoaded -> {
            AuthorScreenUI(
                model = (state as AuthorState.DataLoaded).ownerModel,
                navController = navController,
                context = context
            )
        }
        is AuthorState.Error -> { ErrorDialog(modifier = Modifier, onRetry = {}) }
    }
}


@OptIn(ExperimentalCoilApi::class, androidx.paging.ExperimentalPagingApi::class)
@Composable
fun AuthorScreenUI(
    model: OwnerLocalModel,
    navController: NavController,
    context: Context
) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    )
    {
        val (topBar, image, login, type) = createRefs()

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
                    text = "Detail author`s information",
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
                    context.startActivity(shareAuthorInfo(model))
                })
                {
                    Icon(Icons.Default.Share, "share author`s info", tint = White)
                }
            }
        )

        val painter = rememberImagePainter(data = model.avatar_url)
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

        Text(
            modifier = Modifier
                .constrainAs(login) {
                    top.linkTo(image.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            text = "Login: ${model.login}",
            color = Black,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            textAlign = TextAlign.Center)

        Text(modifier = Modifier
            .constrainAs(type) {
                top.linkTo(login.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(8.dp, 0.dp, 8.dp, 0.dp),
            text = model.type,
            color = Black,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            textAlign = TextAlign.Center)
    }
}