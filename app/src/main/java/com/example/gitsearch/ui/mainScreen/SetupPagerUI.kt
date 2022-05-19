package com.example.gitsearch.ui.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalPagingApi::class)
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
            .padding(0.dp, 132.dp, 0.dp, 0.dp,)
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

enum class TabPage(val icon: ImageVector) {
    Star(Icons.Default.Star),
    Update(Icons.Default.Update)
}