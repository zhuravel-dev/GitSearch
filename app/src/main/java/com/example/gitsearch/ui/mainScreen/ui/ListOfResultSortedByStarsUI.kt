package com.example.gitsearch.ui.mainScreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.ui.compose.theme.Black
import com.example.gitsearch.ui.extensions.parseData

@OptIn(
    androidx.compose.material.ExperimentalMaterialApi::class,
    coil.annotation.ExperimentalCoilApi::class)
@Composable
fun ListOfResultSortedByStarsUI(
    navController: NavController,
    modifier: Modifier,
    userList: LazyPagingItems<ItemLocalModel>
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState, modifier = modifier) {
        itemsIndexed(userList) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(corner = CornerSize(8.dp)),
                onClick = {
                    item?.let {
                        navController.navigate("detail_repo_screen/id=${it.id}")
                    }
                }
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
                            .padding(60.dp, 4.dp, 4.dp, 4.dp)
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
                                    text = "upd.${item?.updated_at?.let { parseData(it) }}"
                                )
                            )
                        }
                        Row {
                            Text(
                                text = login.value?.text + "/",
                                color = Black,
                                fontSize = 20.sp
                            )
                            name.value?.let {
                                Text(
                                    text = it.text,
                                    color = Black,
                                    fontSize = 20.sp
                                )
                            }
                        }
                        description.value?.let {
                            Text(
                                text = it.text,
                                color = MaterialTheme.colors.onBackground,
                                maxLines = 1
                            )
                        }
                        Text(
                            text = topics.value.text,
                            color = MaterialTheme.colors.onBackground,
                            maxLines = 1
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = stars.value.text + "  ",
                                color = MaterialTheme.colors.onBackground,
                                maxLines = 1
                            )
                            Text(
                                text = lang.value?.text + "  ",
                                color = MaterialTheme.colors.onBackground,
                                maxLines = 1
                            )
                            Text(
                                text = date.value.text,
                                color = MaterialTheme.colors.onBackground,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}