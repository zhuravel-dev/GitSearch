package com.example.gitsearch.ui.compose.navigation

import MainScreenWithPagerUI
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.ui.authorScreen.ui.AuthorScreenUI
import com.example.gitsearch.ui.detailRepoScreen.ui.DetailRepoUI
import com.example.gitsearch.ui.mainScreen.MainViewModel

@OptIn(ExperimentalPagingApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>()

    NavHost(navController = navController, startDestination = "main_screen") {

        composable(
            route = "main_screen"
        ) {
            MainScreenWithPagerUI(
                navController = navController,
                viewModel = mainViewModel
            )
        }

        composable(
            route = "detail_repo_screen/id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getInt("id")?.let {
                DetailRepoUI(
                    id = it,
                    navController
                )
            }
        }

        composable(
            route = "detail_author_screen/id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    nullable = false
                })
        ) { entry ->
            entry.arguments?.getInt("id")?.let {
                AuthorScreenUI(
                    id = it,
                    navController,

                )
            }
        }
    }
}