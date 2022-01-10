package com.example.gitsearch.ui.firstFragment

sealed class FirstFragmentIntent {

   object FetchGitList : FirstFragmentIntent()
    data class SearchGitList(var q: String, var page: Int, var perPage: Int) : FirstFragmentIntent()
    data class SetSelectedRepositoryId(val id: Int) : FirstFragmentIntent()

}