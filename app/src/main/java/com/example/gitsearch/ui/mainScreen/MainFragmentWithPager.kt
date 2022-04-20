package com.example.gitsearch.ui.mainScreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.data.remote.model.ItemsResponse
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log


@OptIn(InternalCoroutinesApi::class)
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainFragmentWithPager : Fragment() {

    private val mainViewModel: MainViewModel by viewModels()
    private val usersState = mutableStateListOf<Item>()

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

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun LaunchMainScreen(viewModel: MainViewModel) {

        val resultState by viewModel.state.collectAsState()

/*
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val (search, welcomeText) = createRefs()
*/



        when (resultState) {
            is MainState.Idle -> {
                SearchField(viewModel)
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
                val list = (resultState as? MainState.DataLoaded)?.data?.items ?: listOf()
                usersState.clear()
                usersState.addAll(list)
                Column {
                    SearchField(viewModel)
                    ListOfResult(usersState, Modifier.weight(1f))
                }
            }
            is MainState.Error -> ErrorDialog()
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@Composable
private fun SearchField(viewModel: MainViewModel) {
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
        /*.constrainAs(search) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }*/
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ListOfResult(userList: SnapshotStateList<Item>, modifier: Modifier) {
    val users = remember { userList }
    if(userList.isEmpty()) ErrorDialog().also {
        users.clear()
        return
    }
    val listState = rememberLazyListState()

    LazyColumn(state = listState, modifier = modifier) {
        itemsIndexed(users) { index, item ->
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
                    val login = remember { mutableStateOf(TextFieldValue(text = item.owner.login)) }
                    val name = remember { mutableStateOf(TextFieldValue(text = item.name)) }
                    val description = remember { mutableStateOf(TextFieldValue(text = item.description)) }
                    val topics = remember { mutableStateOf(TextFieldValue(text = item.topics.toString())) }
                    val stars = remember { mutableStateOf(TextFieldValue(text = "\u2606 ${item.stargazers_count}")) }
                    val lang = remember { mutableStateOf(TextFieldValue(text = item.language)) }
                    val date = remember { mutableStateOf(TextFieldValue(text = "Updated ${parseDate(item.updated_at)}")) }

                    Row {
                        Text(
                            text = login.value.text + "/", color = Color.Black, fontSize = 20.sp
                        )
                        Text(
                            text = name.value.text, color = Color.Black, fontSize = 20.sp
                        )
                    }
                    Text(text = description.value.text, color = Color.Gray, maxLines = 1)
                    Text(text = topics.value.text, color = Color.Gray, maxLines = 1)
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            modifier = Modifier.weight(0.5f),
                            text = stars.value.text,
                            color = Color.Gray,
                        )
                        Text(
                            modifier = Modifier.weight(0.5f),
                            text = lang.value.text,
                            color = Color.Gray
                        )
                        Text(
                            modifier = Modifier.weight(0.5f),
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

@RequiresApi(Build.VERSION_CODES.O)
private fun parseDate(notParsed: String): String = try {
    val parsedDate = LocalDateTime.parse(notParsed, DateTimeFormatter.ISO_DATE_TIME)
    parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
} catch (e: Throwable) {""}

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