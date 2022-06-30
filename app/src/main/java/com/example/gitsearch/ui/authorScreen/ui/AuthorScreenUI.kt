package com.example.gitsearch.ui.authorScreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.gitsearch.ui.compose.navigation.fromJsonToModel
import com.example.gitsearch.ui.compose.theme.Black
import com.example.gitsearch.ui.compose.theme.White
import com.example.gitsearch.ui.extensions.shareAuthorInfo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AuthorScreenUI(navController: NavController, id: Int) {
    ConstraintLayout(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    )
    {
        val context = LocalContext.current
        val model = "".fromJsonToModel()


        val (topBar, image, login,
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
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    context.startActivity(shareAuthorInfo(model)) })
                {
                    Icon(Icons.Default.Share, "share author`s info", tint = White)
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
                text = "Login: ${it.login}",
                color = Black,
                style = MaterialTheme.typography.h5,
                maxLines = 1,
                textAlign = TextAlign.Center)
        }
    }
}