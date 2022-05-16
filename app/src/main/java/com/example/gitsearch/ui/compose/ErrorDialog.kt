package com.example.gitsearch.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDialog(modifier: Modifier) {

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Loading error") },
        text = { Text(text = "Please retry!") },
        confirmButton = {
            Button( onClick = { System.exit(0) } ) { Text(text = "OK") }
        }
    )
}

@Preview
@Composable
fun MyErrorDialogPreview() {
    ErrorDialog(modifier = Modifier.size(100.dp, 100.dp))
}