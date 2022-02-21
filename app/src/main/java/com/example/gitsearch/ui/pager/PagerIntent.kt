package com.example.gitsearch.ui.pager

sealed class PagerIntent {
    data class SearchGitListSortedByStars(var q: String) : PagerIntent()
    data class SearchGitListSortedByUpdate(var q: String) : PagerIntent()
}