package com.example.gitsearch.ui.mainScreen

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.gitsearch.data.remote.model.Item
import com.example.gitsearch.ui.compose.CircularProgress
import com.example.gitsearch.ui.compose.ErrorDialog
import com.example.gitsearch.ui.compose.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import timber.log.Timber
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
                    val list = (resultState as? MainState.DataLoaded)?.data?.items ?: listOf()
                    usersState.clear()
                    usersState.addAll(list)

                    SearchField(modifier = Modifier
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
                            }, usersState
                    )
                }
                is MainState.Error -> ErrorDialog(modifier = Modifier.constrainAs(error){
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
            /*.constrainAs(search) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }*/
        )
    }

    @OptIn(ExperimentalCoilApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun ListOfResult(modifier: Modifier, userList: SnapshotStateList<Item>, ) {
        val users = remember { userList }
        if (userList.isEmpty()) ErrorDialog(modifier = Modifier).also {
            users.clear()
            return
        }
        val listState = rememberLazyListState()

        if (listState.layoutInfo.visibleItemsInfo.lastIndex == users.size - 1) {
            Timber.d("End")
        }


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

                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding()
                    ) {
                        val (image, column) = createRefs()
                        val painter = rememberImagePainter(data = item.owner.avatar_url,
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
                                .size(120.dp, 120.dp)
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(column.start)
                                },
                            contentScale = ContentScale.Crop,
                        )

                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .constrainAs(column) {
                                    top.linkTo(parent.top)
                                    start.linkTo(image.end)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                }

                        ) {

                            val login =
                                remember { mutableStateOf(TextFieldValue(text = item.owner.login)) }
                            val name = remember { mutableStateOf(TextFieldValue(text = item.name)) }
                            val description =
                                remember { mutableStateOf(TextFieldValue(text = item.description)) }
                            val topics = remember {
                                mutableStateOf(
                                    TextFieldValue(
                                        text = item.topics.toString()
                                            .substring(1, item.topics.toString().length - 1)
                                    )
                                )
                            }
                            val stars =
                                remember { mutableStateOf(TextFieldValue(text = "\u2606 ${item.stargazers_count}")) }
                            val lang =
                                remember { mutableStateOf(TextFieldValue(text = item.language)) }
                            val date = remember {
                                mutableStateOf(
                                    TextFieldValue(
                                        text = "Updated ${parseDate(item.updated_at)}"
                                    )
                                )
                            }
                            Row {
                                Text(
                                    text = login.value.text + "/",
                                    color = Color.Black,
                                    fontSize = 20.sp
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
                                    modifier = Modifier.weight(0.1f),
                                    text = stars.value.text,
                                    color = Color.Gray,
                                )
                                Text(
                                    modifier = Modifier.weight(0.1f),
                                    text = lang.value.text,
                                    color = Color.Gray
                                )
                                Text(
                                    modifier = Modifier.weight(0.3f),
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