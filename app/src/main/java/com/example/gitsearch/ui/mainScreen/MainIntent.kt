package com.example.gitsearch.ui.mainScreen

sealed class MainIntent {
    data class SearchGitList(var q: String) : MainIntent()
}