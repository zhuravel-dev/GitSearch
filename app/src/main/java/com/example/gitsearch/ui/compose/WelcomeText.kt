package com.example.gitsearch.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeText(modifier: Modifier) {
    Text(
        text = "Search something on GitHub!",
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        color = Color.Gray,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 320.dp)
    )
}