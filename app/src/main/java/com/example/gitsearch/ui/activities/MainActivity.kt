package com.example.gitsearch.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.gitsearch.ui.compose.navigation.Navigation
import com.example.gitsearch.ui.compose.theme.AppTheme
import com.example.gitsearch.ui.mainScreen.MainViewModel
import com.example.gitsearch.ui.mainScreen.ui.MyAppBar
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterialApi::class, androidx.paging.ExperimentalPagingApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = isSystemInDarkTheme()) {
                Scaffold(topBar = { MyAppBar(viewModel = viewModel) }) {
                    Navigation()
                }
            }
        }
    }
}