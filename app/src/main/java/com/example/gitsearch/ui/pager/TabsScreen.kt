package com.example.gitsearch.ui.pager

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Update
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabPage(val icon: ImageVector) {
    Stars(Icons.Default.Star),
    Update(Icons.Default.Update)
}

@Composable
fun TabStars(selectedTabIndex: Int, onSelectedTab: (TabPage) -> Unit) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        TabPage.values().forEachIndexed { index, tabPage ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(tabPage) },
                text = { Text(text = "Sorting by " + tabPage.name, style = MaterialTheme.typography.body1) },
                icon = { Icon(imageVector = tabPage.icon, contentDescription = null) },
                selectedContentColor = Color.White,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled)
            )
        }
    }
}