package com.example.gitsearch.ui.firstFragment

sealed class FirstFragmentIntent {

    object FetchGitList : FirstFragmentIntent()
    data class SearchGitList(var q: String) : FirstFragmentIntent()

}