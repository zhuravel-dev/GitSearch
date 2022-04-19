package com.example.gitsearch.ui.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.remote.model.ItemsResponse
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
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
                    LaunchMainScreen(viewModel = mainViewModel)
                }
            }
        }
    }

    @Composable
    private fun LaunchMainScreen(viewModel: MainViewModel) {

        val resultState by viewModel.state.collectAsState()
        val textState = remember { mutableStateOf("") }

/*
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val (search, welcomeText) = createRefs()
*/

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
            /*.constrainAs(search) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }*/
        )

        when (resultState) {
            is MainState.Idle -> {
                WelcomeText()

                /*(modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(welcomeText) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })*/
            }
            is MainState.Loading -> {
                CircularProgress()
            }
            is MainState.DataLoaded -> {
                ListOfResult((resultState as MainState.DataLoaded).data)
            }
            is MainState.Error -> ErrorDialog()
        }
    }
}

@Composable
private fun ListOfResult(userList: ItemsResponse) {

    val listState = rememberLazyListState()
    //val parsedDate = LocalDateTime.parse(updatedDate, DateTimeFormatter.ISO_DATE_TIME)
    //val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    LazyColumn(state = listState) {
        itemsIndexed(userList.items) { index, item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = 4.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(corner = CornerSize(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()

                ) {
                    Row() {
                        Text(
                            text = item.owner.login + "/", color = Color.Black, fontSize = 20.sp
                        )
                        Text(
                            text = item.name, color = Color.Black, fontSize = 20.sp
                        )
                    }
                    Text(text = item.description, color = Color.Gray, maxLines = 1)
                    Text(text = item.topics.toString(), color = Color.Gray, maxLines = 1)
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            modifier = Modifier.weight(0.5f),
                            text = "\u2606 ${item.stargazers_count}",
                            color = Color.Gray,
                        )
                        Text(
                            modifier = Modifier.weight(0.5f),
                            text = item.language,
                            color = Color.Gray
                        )
                        Text(
                            modifier = Modifier.weight(0.5f),
                            text = "Updated ${item.updated_at.length}",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

/* if (listState.layoutInfo.visibleItemsInfo.lastIndex == userList.items.size) {
     Timber.d("End")*/


@Composable
private fun WelcomeText() {
    Text(
        text = "Search something on GitHub!",
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 320.dp)
    )
}