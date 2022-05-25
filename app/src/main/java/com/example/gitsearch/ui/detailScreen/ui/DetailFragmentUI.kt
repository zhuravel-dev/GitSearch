package com.example.gitsearch.ui.detailScreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.theme.Gray
import com.example.gitsearch.ui.compose.theme.White

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DetailFragmentUI(model: ItemLocalModel, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .background(White)
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
                    text = "Detail information",
                    color = White,
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

        model.owner?.let {
            Text(
                modifier = Modifier
                    .constrainAs(login) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp, 0.dp, 8.dp, 0.dp),
                text = it.login,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
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
            color = Gray,
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
                color = Gray,
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
                color = Gray,
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
            color = Gray,
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
            color = Gray,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center)
    }
}