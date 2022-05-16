package com.example.gitsearch.ui.mainScreen

import TabPage
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.WelcomeText
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainFragmentWithPager : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                /* Surface(color = MaterialTheme.colors.background) {
                      Scaffold(
                          topBar = { MyAppBar(viewModel = mainViewModel) }
                      ) {
                          LaunchMainScreen(viewModel = mainViewModel)
                      }
                  }*/

                AppTheme {
                    LaunchMainScreen(viewModel = mainViewModel)
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun LaunchMainScreen(viewModel: MainViewModel) {

        val resultState by viewModel.state.collectAsState()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val (topAppBar, tabs, pager, welcomeText, progress, results, error) = createRefs()

            val scope = rememberCoroutineScope()
            val pages = remember { listOf("Sorting bu stars", "Sorting by update") }
            val pagerState = rememberPagerState(pageCount = pages.size)

            TabRow(selectedTabIndex = pagerState.currentPage, modifier = Modifier
                // .padding(0.dp, 56.dp, 0.dp, 0.dp)
                .constrainAs(tabs) {
                    top.linkTo(topAppBar.bottom)
                    start.linkTo(topAppBar.start)
                    end.linkTo(topAppBar.end)
                }) {
                TabPage.values().forEachIndexed { index, tabPage ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.scrollToPage(index) } },
                        text = {
                            Text(
                                text = "Sorting by " + tabPage.name,
                                style = typography.body1
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

            val textState = remember { mutableStateOf("") }

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
                                modifier = Modifier.alpha(medium)
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = typography.subtitle1.fontSize
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

            when (resultState) {
                is MainState.Idle -> {
                    WelcomeText(modifier = Modifier
                        .constrainAs(welcomeText) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        })
                }

                is MainState.Loading -> {
                    CircularProgress(modifier = Modifier.constrainAs(progress) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    })
                }

                is MainState.DataLoaded -> {
                    val listStars =
                        (resultState as MainState.DataLoaded).dataByStars.collectAsLazyPagingItems()
                    val listUpdate =
                        (resultState as MainState.DataLoaded).dataByUpdate.collectAsLazyPagingItems()
                    this@ConstraintLayout.setupPager(
                        pager,
                        tabs,
                        pagerState = pagerState,
                        userListByStars = listStars,
                        userListByUpdate = listUpdate
                    )
                }
                is MainState.Error -> ErrorDialog(modifier = Modifier.constrainAs(error) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })
            }
        }
    }

    @ExperimentalPagerApi
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun ConstraintLayoutScope.setupPager(
        pager: ConstrainedLayoutReference,
        tabs: ConstrainedLayoutReference,
        userListByStars: LazyPagingItems<ItemLocalModel>? = null,
        userListByUpdate: LazyPagingItems<ItemLocalModel>? = null,
        pagerState: PagerState,
    ) {
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .constrainAs(pager) {
                    top.linkTo(tabs.bottom)
                    start.linkTo(tabs.start)
                    end.linkTo(tabs.end)
                }
        ) { index ->
            when (index) {
                0 -> userListByStars?.let {
                    ListOfResultSortedByStarsUI(
                        modifier = Modifier
                            .height(648.dp),
                        userList = it,
                        onClick = {
                            findNavController().navigate(
                                MainFragmentWithPagerDirections.actionToDetailFragment(it)
                            )
                        }
                    )
                }
                1 -> userListByUpdate?.let {
                    ListOfResultSortedByUpdateUI(
                        modifier = Modifier
                            .height(648.dp),
                        userList = it,
                        onClick = {
                            findNavController().navigate(
                                MainFragmentWithPagerDirections.actionToDetailFragment(it)
                            )
                        })
                }
            }
        }
    }
}

/*
@OptIn(ExperimentalPagingApi::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun MyAppBar(viewModel: MainViewModel) {

    val textState = remember { mutableStateOf("") }

    TopAppBar(
        title = {},
        actions = {
            TextField(
                value = textState.value,
                onValueChange = { text ->
                    textState.value = text
                    if (textState.value.length >= 3) {
                        viewModel.onIntent(MainIntent.SearchGitListSortedByStars(textState.value))
                    }
                },
                onValueChange = { text ->
                            textState.value = text
                            if (textState.value.length >= 3 && pagerState.currentPage == 0 ) {
                                viewModel.onIntent(MainIntent.SearchGitListSortedByStars(textState.value))
                            }
                            if (textState.value.length >= 3 && pagerState.currentPage == 1 ) {
                                viewModel.onIntent(MainIntent.SearchGitListSortedByUpdate(textState.value))
                            }
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search...",
                        color = Color.White,
                        modifier = Modifier.alpha(medium)
                    )
                },
                textStyle = TextStyle(
                    fontSize = typography.subtitle1.fontSize
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
}*/