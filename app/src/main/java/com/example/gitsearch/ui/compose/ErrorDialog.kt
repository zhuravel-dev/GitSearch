package com.example.gitsearch.ui.compose

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ErrorDialog(
    modifier: Modifier,
    onRetry: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; })
                { Text(text = "Retry") }
            },
            title = { Text(text = "Loading error") },
            text = { Text(text = "Please retry") }
        )
    }
}