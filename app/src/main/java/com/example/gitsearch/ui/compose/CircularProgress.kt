package com.example.gitsearch.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun ConstraintLayoutScope.CircularProgress(
    progress: ConstrainedLayoutReference,
) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(64.dp)
            .constrainAs(progress) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
        color = MaterialTheme.colors.primary
    )
}