package com.example.gitsearch.ui.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgress(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(64.dp),
        color = MaterialTheme.colors.primary
    )
}