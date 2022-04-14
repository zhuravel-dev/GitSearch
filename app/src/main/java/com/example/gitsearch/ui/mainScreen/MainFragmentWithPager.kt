package com.example.gitsearch.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow

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

        val resultState by viewModel.state.collectAsState()
        val textState = remember { mutableStateOf("") }

        LaunchedEffect(true) {
            viewModel.onIntent(MainIntent.SearchGitListSortedByStars(textState.value))
        }

        /* LaunchedEffect(textState) {
            if (textState.value.length >= 3) {
                viewModel.onIntent(MainIntent.SearchGitListSortedByStars(textState.value))
            }
        }*/

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val search = createRef()

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
                    .constrainAs(search) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            when (resultState) {
                is MainState.Idle -> {
                    CircularProgress()
                }
                is MainState.Loading -> {}
                is MainState.DataLoaded -> {
                    ListOfResult((resultState as MainState.DataLoaded).data)
                }
                is MainState.Error -> ErrorDialog()
            }
        }
    }

    @Composable
    private fun ListOfResult(userList: Flow<PagingData<ItemLocalModel>>) {

        val userListItems: LazyPagingItems<ItemLocalModel> = userList.collectAsLazyPagingItems()

        /*LazyColumn {
            items(userListItems) { item ->
                UserList(item)
            }
        }*/

        LazyColumn {
            items(userListItems.itemCount) { index ->
                userListItems[index]?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it.name,
                        color = Color.Black
                    )
                }
            }
        }
    }

        @Composable
       fun UserList(model: ItemLocalModel?) {

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = 4.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(corner = CornerSize(8.dp))
            ) {
                Row {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    ) {
                        model?.name?.let { Text(text = it, style = typography.h6) }
                        model?.description?.let { Text(text = it, style = typography.caption) }
                    }
                }
            }
        }

    }