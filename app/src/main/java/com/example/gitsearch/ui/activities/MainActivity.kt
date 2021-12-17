package com.example.gitsearch.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gitsearch.databinding.ActivityMainBinding
import com.example.gitsearch.ui.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding (ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }
}