package com.example.gitsearch.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

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
                    SetupUI(MainViewModel = mainViewModel)
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class, androidx.paging.ExperimentalPagingApi::class)
    @Composable
    private fun SetupUI(MainViewModel: MainViewModel) {
        /*var tabPage by remember { mutableStateOf(TabPage.Star) }
        val pagerState = rememberPagerState(pageCount = TabPage.values().size)
        val scope = rememberCoroutineScope()*/
        val textState = remember { mutableStateOf(TextFieldValue("")) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (topBar, tabs, pager) = createRefs()

            OutlinedTextField(
                value = textState.value,
                // onValueChange = { text = it },
                onValueChange = { value ->
                    textState.value = value
                },
                label = { Text("Search on GitHab...") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .constrainAs(topBar) {
                        top.linkTo(parent.top, 4.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                //keyboardActions = KeyboardActions(onSearch = { onSearch() }),
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

            Text(
                text = "Test1",
                modifier = Modifier
                    .constrainAs(tabs) {
                        top.linkTo(topBar.bottom, 50.dp)
                        start.linkTo(topBar.start)
                        end.linkTo(topBar.end)
                    }
            )
        }


            /* TopAppBar(
                 modifier = Modifier
                     .constrainAs(topBar) {
                         top.linkTo(parent.top, 80.dp)
                         start.linkTo(parent.start)
                         end.linkTo(parent.end)
                     },
                 backgroundColor = MaterialTheme.colors.primary,
                 title = {
                     Text(
                         text = "GitSearch",
                         color = Color.White,
                         modifier = Modifier.fillMaxWidth(),
                         textAlign = TextAlign.Start,
                         //style = MaterialTheme.typography.h6
                     )
                 },
                 navigationIcon = {
                     IconButton(onClick = { System.out }) {
                         Icon(
                             imageVector = Icons.Filled.ArrowBack,
                             contentDescription = "Back"
                         )
                     }
                 },
                 actions = {

                     IconButton(onClick = {  }) {
                         Icon(
                             imageVector = Icons.Default.Search,
                             contentDescription = "Search",
                             tint = Color.White
                         )
                     }
                 }
             )

             Search(state = textState, modifier = Modifier
                 .constrainAs(tabs) {
                     top.linkTo(topBar.bottom)
                     start.linkTo(topBar.start)
                     end.linkTo(topBar.end)
                 }
             )*/


            /*SelectTab(selectedTabIndex = pagerState.currentPage, onSelectedTab = {
                scope.launch {
                    pagerState.animateScrollToPage(it.ordinal)
                }
            },
                modifier = Modifier
                    .constrainAs(tabs) {
                        top.linkTo(topBar.bottom, 70.dp)
                        start.linkTo(topBar.start)
                        end.linkTo(topBar.end)
                    }
            )*/

            /*HorizontalPager(state = pagerState,
                modifier = Modifier
                    .padding(12.dp)
                    .constrainAs(pager) {
                        top.linkTo(tabs.bottom)
                        start.linkTo(tabs.start)
                        end.linkTo(tabs.end)
                    }

            ) { index ->
                Column(Modifier.fillMaxSize()) {
                    when (index) {
                        // 0 -> FragmentSortingByStars()
                        1 -> Text(text = "Test2")
                    }
                }
            }*/
        }
    }