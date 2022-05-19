package com.example.gitsearch.ui.mainScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class,
    com.google.accompanist.pager.ExperimentalPagerApi::class,
    kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun ConstraintLayoutScope.MyAppBar(
    viewModel: MainViewModel,
    topAppBar: ConstrainedLayoutReference,
) {
    val textState = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val pages = remember { listOf("Sorting by stars", "Sorting by update") }
    val pagerState = rememberPagerState(pageCount = pages.size)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                modifier = Modifier.constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
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
                            if (textState.value.length >= 3) {
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
                })
        },
        floatingActionButton = {},
        drawerContent = { },
        content = {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                TabPage.values().forEachIndexed { index, tabPage ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.scrollToPage(index) } },
                        text = {
                            Text(
                                text = "Sorting by " + tabPage.name,
                                style = MaterialTheme.typography.body1
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tabPage.icon,
                                contentDescription = null
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(
                            ContentAlpha.disabled
                        )
                    )
                }
            }
        },
        bottomBar = {},
    )
}