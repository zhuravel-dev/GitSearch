package com.example.gitsearch.ui.firstFragment

sealed class FirstFragmentIntent {

    object FetchGitList : FirstFragmentIntent()
    data class SearchGitList(var q: String) : FirstFragmentIntent()
    data class SetSelectedRepositoryId(val id: Int) : FirstFragmentIntent()

}