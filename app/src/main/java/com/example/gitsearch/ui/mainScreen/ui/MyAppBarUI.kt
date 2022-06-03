package com.example.gitsearch.ui.mainScreen.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.ui.mainScreen.MainIntent
import com.example.gitsearch.ui.mainScreen.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagingApi::class)
@Composable
fun MyAppBar(
    viewModel: MainViewModel,
) {
    val textState = remember { mutableStateOf("") }

    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            TextField(
                value = textState.value,
                onValueChange = { text ->
                    textState.value = text
                    if (textState.value.length >= 2) {
                        viewModel.onIntent(MainIntent.SearchGitList(textState.value))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color.White,
                        modifier = Modifier.alpha(ContentAlpha.medium)
                    )
                },
                textStyle = TextStyle(
                    fontSize = MaterialTheme.typography.subtitle1.fontSize
                ),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.alpha(ContentAlpha.medium)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "SearchImage",
                            tint = Color.White
                        )
                    }
                },
            )
        }
    )
}