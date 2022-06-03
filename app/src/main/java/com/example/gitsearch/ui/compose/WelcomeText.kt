package com.example.gitsearch.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun ConstraintLayoutScope.WelcomeText(
    welcomeText: ConstrainedLayoutReference,
    topAppBar: ConstrainedLayoutReference
) {
    Text(
        text = "Search something on GitHub!",
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .constrainAs(welcomeText) {
                top.linkTo(topAppBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(top = 240.dp)
    )
}