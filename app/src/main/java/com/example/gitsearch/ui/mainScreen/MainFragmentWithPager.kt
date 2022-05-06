package com.example.gitsearch.ui.mainScreen

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun LaunchMainScreen(viewModel: MainViewModel) {

        val resultState by viewModel.state.collectAsState()

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEAECEC))
        ) {
            val (search, welcomeText, progress, results, error) = createRefs()

            when (resultState) {
                is MainState.Idle -> {
                    SearchField(
                        modifier = Modifier
                            .constrainAs(search) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }, viewModel
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

                    ListOfResult(
                        Modifier
                            .height(648.dp)
                            .constrainAs(results) {
                                top.linkTo(search.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }, list
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

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalCoilApi::class)
    @ExperimentalPagingApi
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun ListOfResult(
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDate(notParsed: String): String = try {
        val parsedDate = LocalDateTime.parse(notParsed, DateTimeFormatter.ISO_DATE_TIME)
        parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    } catch (e: Throwable) {
        ""
    }

    @Composable
    private fun WelcomeText(modifier: Modifier) {
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
}