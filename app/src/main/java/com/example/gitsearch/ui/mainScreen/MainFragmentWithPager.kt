package com.example.gitsearch.ui.mainScreen

import TabPage
import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.domain.repository.MainRepository
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.example.gitsearch.ui.detailFragment.DetailFragmentIntent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(InternalCoroutinesApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainFragmentWithPager : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()

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

    @Composable
    private fun LaunchMainScreen(viewModel: MainViewModel) {

        val viewState by viewModel.state.collectAsState()

        LaunchedEffect(true) {
            delay(1000)
            viewModel.onIntent(MainIntent.SearchGitListSortedByStars(q = "Hello"))
        }

        when (viewState) {
            is MainState.Idle -> {
                CircularProgress()
            }
            is MainState.Loading -> {}
            is MainState.DataLoaded -> SetupUI()
            is MainState.Error -> ErrorDialog()
        }
    }

    @OptIn(ExperimentalPagerApi::class, ExperimentalPagingApi::class)
    @Composable
    private fun SetupUI(onImeAction: (String) -> Unit = {}) {
        val textState = remember { mutableStateOf(TextFieldValue("")) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val (search, tabs, pager) = createRefs()

            OutlinedTextField(
                value = textState.value,
                onValueChange = { text ->
                    textState.value = text
                },
                label = { Text("Search on GitHab...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(search) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None,
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                  keyboardActions = KeyboardActions(onSearch = {
                      Log.d("SEARCH", "keyboardActions")
                      onImeAction("")
                  }),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        tint = Color.Gray
                    )
                },
                trailingIcon = {
                    if (textState.value != TextFieldValue("")) {
                        IconButton(
                            onClick = {
                                textState.value =
                                    TextFieldValue("")
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(15.dp)
                                    .size(24.dp)
                            )
                        }
                    }
                },
            )
            val scope = rememberCoroutineScope()
            val pages = remember { listOf("Sorting bu stars", "Sorting by update") }
            val pagerState = rememberPagerState(
                pageCount = pages.size
            )

            TabRow(selectedTabIndex = pagerState.currentPage, modifier = Modifier
                .padding(8.dp)
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
                                style = MaterialTheme.typography.body1
                            )
                        },
                        icon = { Icon(imageVector = tabPage.icon, contentDescription = null) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
                    )
                }
            }

            HorizontalPager(state = pagerState,
                modifier = Modifier
                    .padding(12.dp)
                    .constrainAs(pager) {
                        top.linkTo(tabs.bottom)
                        start.linkTo(tabs.start)
                        end.linkTo(tabs.end)
                    }
            ) { index ->
                when (index) {
                    0 -> FragmentSortingByStars()
                    // 1 -> Recycler()
                }
            }
        }
    }

    @Composable
    private fun Recycler(model: List<ItemLocalModel>) {

        LazyColumn() {

            items(model) { model ->
                Card(model)
            }
        }
    }

    @Composable
    private fun Card(model: ItemLocalModel) {

        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(8.dp))
        ) {
            Row() {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = model.name, style = typography.h6)
                    model.description?.let { Text(text = it, style = typography.caption) }
                }
            }
        }
    }
}