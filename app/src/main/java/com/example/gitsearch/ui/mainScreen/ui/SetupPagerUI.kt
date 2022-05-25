package com.example.gitsearch.ui.mainScreen.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ConstraintLayoutScope.SetupPager(
    topAppBar: ConstrainedLayoutReference,
    pager: ConstrainedLayoutReference,
    userListByStars: LazyPagingItems<ItemLocalModel>? = null,
    userListByUpdate: LazyPagingItems<ItemLocalModel>? = null,
    pagerState: PagerState,
    onClick: (ItemLocalModel) -> Unit
) {
    HorizontalPager(state = pagerState,
        modifier = Modifier
            .constrainAs(pager) {
                top.linkTo(topAppBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
    ) { index ->
        when (index) {
            0 -> userListByStars?.let {
                ListOfResultSortedByStarsUI(
                    modifier = Modifier
                        .height(648.dp),
                    userList = it,
                    onClick = {
                        onClick(it)
                    }
                )
            }
            1 -> userListByUpdate?.let {
                ListOfResultSortedByUpdateUI(
                    modifier = Modifier
                        .height(648.dp),
                    userList = it,
                    onClick = {
                        onClick(it)
                    })
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs() {
    val scope = rememberCoroutineScope()
    val pages = remember { listOf("Sorting by stars", "Sorting by update") }
    val pagerState = rememberPagerState(pageCount = pages.size)

    TabRow(selectedTabIndex = pagerState.currentPage) {
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
}

enum class TabPage(val icon: ImageVector) {
    Star(Icons.Default.Star),
    Update(Icons.Default.Update)
}