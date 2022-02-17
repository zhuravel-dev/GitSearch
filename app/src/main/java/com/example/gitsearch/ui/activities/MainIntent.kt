package com.example.gitsearch.ui.activities

sealed class MainIntent {

    data class SearchGitListSortedByStars(var q: String) : MainIntent()
    data class SearchGitListSortedByUpdate(var q: String) : MainIntent()
}