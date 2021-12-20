package com.example.gitsearch.ui.firstFragment

sealed class FirstFragmentIntent {

    object FetchGitList : FirstFragmentIntent()
    data class SearchGitList(val query: String) : FirstFragmentIntent()

}