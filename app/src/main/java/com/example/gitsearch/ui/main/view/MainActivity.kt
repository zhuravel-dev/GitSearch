package com.example.gitsearch.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.ActivityMainBinding
import com.example.gitsearch.ui.main.adapter.MainAdapter
import com.example.gitsearch.ui.main.extensions.viewBinding
import com.example.gitsearch.ui.main.intent.MainIntent
import com.example.gitsearch.ui.main.viewmodel.MainViewModel
import com.example.gitsearch.ui.main.viewstate.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var adapter = MainAdapter(arrayListOf())
    private val mainViewModel: MainViewModel by viewModels()
    private val viewBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupUI()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        viewBinding.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }

    private fun setupUI()  = viewBinding.run {
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() = viewBinding.run {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {}
                    is MainState.Loading -> {
                        buttonFetchUser.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Repository -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.GONE
                        renderList(it.repo)
                    }
                    is MainState.Error -> {
                        progressBar.visibility = View.GONE
                        buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(repo: List<Item>) {
        viewBinding.recyclerView.visibility = View.VISIBLE
        repo.let { listOfRepository -> listOfRepository.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}