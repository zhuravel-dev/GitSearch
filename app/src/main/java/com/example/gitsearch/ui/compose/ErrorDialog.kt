package com.example.gitsearch.ui.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(modifier: androidx.compose.ui.Modifier) {

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Loading error") },
        text = { Text(text = "Please, retry!") },
        buttons = {
            Button(onClick = {}) {}
        }
    )
}