package com.example.gitsearch.ui.mainScreen

import TabPage
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.WelcomeText
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
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
    private val usersState = mutableListOf<ItemLocalModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
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
            val (search, tabs, pager, welcomeText, progress, results, error) = createRefs()

            val scope = rememberCoroutineScope()
            val pages = remember { listOf("Sorting bu stars", "Sorting by update") }
            val pagerState = rememberPagerState(
                pageCount = pages.size
            )

            TabRow(selectedTabIndex = pagerState.currentPage, modifier = Modifier
                .padding(8.dp, 72.dp, 8.dp, 0.dp)
                .constrainAs(tabs) {
                    top.linkTo(search.bottom)
                    start.linkTo(search.start)
                    end.linkTo(search.end)
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

            when (resultState) {
                is MainState.Idle -> {
                    /*SearchField(
                        modifier = Modifier
                            .constrainAs(search) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }, viewModel
                    )*/

                    val textState = remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = textState.value,
                        onValueChange = { text ->
                            textState.value = text
                            if (textState.value.length >= 3 && pagerState.currentPage == 0 ) {
                                viewModel.onIntent(MainIntent.SearchGitListSortedByStars(textState.value))
                            }
                            if (textState.value.length >= 3 && pagerState.currentPage == 1 ) {
                                viewModel.onIntent(MainIntent.SearchGitListSortedByUpdate(textState.value))
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

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
                    val list = (resultState as MainState.DataLoaded).data.collectAsLazyPagingItems()
                    /*  usersState.clear()
                      usersState.addAll()*/

                    SearchField(
                        modifier = Modifier
                            .constrainAs(search) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(results.top)
                            }, viewModel
                    )

                    HorizontalPager(state = pagerState,
                        modifier = Modifier
                            .constrainAs(pager) {
                                top.linkTo(tabs.bottom)
                                start.linkTo(tabs.start)
                                end.linkTo(tabs.end)
                            }
                    ) { index ->
                        when (index) {
                            0 -> FragmentWithSortingByStars().ListOfResultSortedByStars1(
                                modifier = Modifier
                                    .height(648.dp),
                                userList = list
                            )
                            1 -> FragmentWithSortingByUpdate().ListOfResultSortedByUpdate(
                                modifier = Modifier
                                    .height(648.dp),
                                userList = list
                            )
                        }
                    }

                   /* ListOfResultSortedByStars(
                        Modifier
                            .height(648.dp)
                            .constrainAs(results) {
                                top.linkTo(search.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }, list
                    )*/
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

    @OptIn(ExperimentalPagingApi::class)
    @Composable
    private fun SearchField(modifier: Modifier, viewModel: MainViewModel) {
        val textState = remember { mutableStateOf("") }

        OutlinedTextField(
            value = textState.value,
            onValueChange = { text ->
                textState.value = text
                if (textState.value.length >= 3) {
                    viewModel.onIntent(MainIntent.SearchGitListSortedByStars(textState.value))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    /*@SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalCoilApi::class)
    @ExperimentalPagingApi
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun ListOfResultSortedByStars(
        modifier: Modifier,
        userList: LazyPagingItems<ItemLocalModel>,
    ) {
        val listState = rememberLazyListState()

        LazyColumn(state = listState, modifier = modifier) {
            itemsIndexed(userList) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        findNavController().navigate(
                            MainFragmentWithPagerDirections.actionToDetailFragment(item!!)
                        )
                    },
                    elevation = 4.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(corner = CornerSize(8.dp))
                ) {
                    ConstraintLayout(
                        modifier = Modifier,
                    ) {
                        val (image, column) = createRefs()

                        val painter = rememberImagePainter(data = item?.owner?.avatar_url,
                            builder = {
                                transformations(
                                    CircleCropTransformation()
                                )
                            }
                        )

                        Image(
                            painter = painter,
                            contentDescription = "User Avatar",
                            modifier = Modifier
                                .size(140.dp, 100.dp)
                                .padding(44.dp, 4.dp, 4.dp, 4.dp)
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(column.start)
                                },
                            alignment = Alignment.Center
                        )

                        Column(
                            modifier = Modifier
                                .width(320.dp)
                                .constrainAs(column) {
                                    top.linkTo(parent.top)
                                    start.linkTo(image.end)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                },
                        ) {
                            val login =
                                remember { mutableStateOf(item?.owner?.let { TextFieldValue(text = it.login) }) }
                            val name =
                                remember { mutableStateOf(item?.name?.let { TextFieldValue(text = it) }) }
                            val description =
                                remember {
                                    mutableStateOf(item?.description?.let {
                                        TextFieldValue(
                                            text = it
                                        )
                                    })
                                }
                            val topics = remember {
                                mutableStateOf(
                                    TextFieldValue(
                                        text = item?.topics.toString()
                                            .substring(1, item?.topics.toString().length - 1)
                                    )
                                )
                            }
                            val stars =
                                remember { mutableStateOf(TextFieldValue(text = "\u2606${item?.stargazers_count}")) }
                            val lang =
                                remember { mutableStateOf(item?.language?.let { TextFieldValue(text = it) }) }
                            val date = remember {
                                mutableStateOf(
                                    TextFieldValue(
                                        text = "upd.${item?.updated_at?.let { parseDate(it) }}"
                                    )
                                )
                            }
                            Row {
                                Text(
                                    text = login.value?.text + "/",
                                    color = Color.Black,
                                    fontSize = 20.sp
                                )
                                name.value?.let {
                                    Text(
                                        text = it.text,
                                        color = Color.Black,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                            description.value?.let {
                                Text(
                                    text = it.text,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                            Text(text = topics.value.text, color = Color.Gray, maxLines = 1)
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = stars.value.text + "  ",
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                                Text(
                                    text = lang.value?.text + "  ",
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                                Text(
                                    text = date.value.text,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }*/
}