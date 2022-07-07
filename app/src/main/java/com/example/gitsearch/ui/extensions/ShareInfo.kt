package com.example.gitsearch.ui.extensions

import android.content.Intent
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.data.local.model.OwnerLocalModel

fun shareRepoInfo(model: ItemLocalModel): Intent {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, model.url)
        type = "text/plain"
    }
    return Intent.createChooser(sendIntent, null)
}

fun shareAuthorInfo(model: OwnerLocalModel): Intent {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, model.url)
        type = "text/plain"
    }
    return Intent.createChooser(sendIntent, null)
}