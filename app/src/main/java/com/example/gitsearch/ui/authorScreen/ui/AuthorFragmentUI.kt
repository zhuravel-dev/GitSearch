package com.example.gitsearch.ui.authorScreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.theme.Black

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AuthorFragmentUI(model: ItemLocalModel, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    )
    {
        val (topBar, login,
            followers, following, repositories, organization, gists) = createRefs()

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
                IconButton(onClick = { onClick() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        model.owner?.let {
            Text(
                modifier = Modifier
                    .constrainAs(login) {
                        top.linkTo(topBar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                text = "Login: ${it.login}",
                color = Black,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                textAlign = TextAlign.Center)
        }

        Text(
            text = "Repositories: ${model.owner?.repos_url?.length}",
            modifier = Modifier
                .constrainAs(repositories) {
                    top.linkTo(login.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            textAlign = TextAlign.Center)

        model.owner?.let {
            Text(
                text = "Followers: ${it.followers_url.length}",
                modifier = Modifier
                    .constrainAs(followers) {
                        top.linkTo(repositories.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                textAlign = TextAlign.Center)
        }

        model.owner?.let {
            Text(
                text = "Following: ${it.following_url.length}",
                modifier = Modifier
                    .constrainAs(following) {
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                textAlign = TextAlign.Center)
        }

        Text(
            text = "Gists: ${model.owner?.gists_url?.length}",
            modifier = Modifier
                .constrainAs(gists) {
                    top.linkTo(following.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(8.dp, 0.dp, 8.dp, 0.dp),
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            textAlign = TextAlign.Center)

        model.owner?.subscriptions_url?.length.let {
            Text(
                text = "Subscriptions: $it",
                modifier = Modifier
                    .constrainAs(organization) {
                        top.linkTo(gists.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                textAlign = TextAlign.Center)
        }
    }
}