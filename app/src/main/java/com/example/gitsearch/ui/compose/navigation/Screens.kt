package com.example.gitsearch.ui.compose.navigation

sealed class Screens(val route: String){
    object MainScreenWithPagerUI : Screens ("main_screen")
    object DetailRepoUI : Screens ("detail_repo_screen")
    object AuthorScreenUI : Screens ("detail_author_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}