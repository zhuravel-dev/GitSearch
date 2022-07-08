package com.example.gitsearch.ui.mainScreen.ui

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun ConstraintLayoutScope.SetupPager(
    navController: NavController,
    topAppBar: ConstrainedLayoutReference,
    tabs: ConstrainedLayoutReference,
    pager: ConstrainedLayoutReference,
    userListByStars: LazyPagingItems<ItemLocalModel>? = null,
    userListByUpdate: LazyPagingItems<ItemLocalModel>? = null,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()

    TabRow(modifier = Modifier
        .constrainAs(tabs) {
        top.linkTo(topAppBar.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }, selectedTabIndex = pagerState.currentPage) {
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
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = MaterialTheme.colors.onPrimary.copy(
                    ContentAlpha.disabled
                )
            )
        }
    }

    HorizontalPager(state = pagerState,
        modifier = Modifier
            .constrainAs(pager) {
                top.linkTo(tabs.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
    ) { index ->
        when (index) {
            0 -> userListByStars?.let {
                ListOfResultSortedByStarsUI(
                    navController,
                    modifier = Modifier
                        .height(648.dp),
                    userList = it
                )
            }
            1 -> userListByUpdate?.let {
                ListOfResultSortedByUpdateUI(
                    navController,
                    modifier = Modifier
                        .height(648.dp),
                    userList = it
                )
            }
        }
    }
}

enum class TabPage(val icon: ImageVector) {
    Star(Icons.Default.Star),
    Update(Icons.Default.Update)
}