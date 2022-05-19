package com.example.gitsearch.ui.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun ConstraintLayoutScope.ErrorDialog(
    error: ConstrainedLayoutReference,
) {

    AlertDialog(
        modifier = Modifier.constrainAs(error) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        },
        onDismissRequest = {},
        title = { Text(text = "Loading error") },
        text = { Text(text = "Please retry!") },
        confirmButton = {
            Button(onClick = { System.exit(0) }) { Text(text = "OK") }
        }
    )
}