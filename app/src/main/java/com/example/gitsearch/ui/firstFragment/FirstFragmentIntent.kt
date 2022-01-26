package com.example.gitsearch.ui.firstFragment

sealed class FirstFragmentIntent {

    data class SearchGitList(var q: String) : FirstFragmentIntent()
}