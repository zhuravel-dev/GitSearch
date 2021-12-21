package com.example.gitsearch.ui.firstFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.FragmentFirstBinding
import com.example.gitsearch.ui.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.fragment_first), SearchView.OnQueryTextListener {

    private val viewBinding by viewBinding(FragmentFirstBinding::bind)
    private val recyclerAdapter by lazy { FirstFragmentAdapter() }
    private val firstFragmentViewModel: FirstFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                firstFragmentViewModel.onIntent(FirstFragmentIntent.FetchGitList)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() = viewBinding.run {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = recyclerAdapter
        toolbar.toolbar.setNavigationOnClickListener {
            System.exit(0)
        }
        toolbar.searchView.setOnQueryTextListener(this@FirstFragment)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            firstFragmentViewModel.state.collect {
                when (it) {
                    is FirstFragmentState.Idle -> {}
                    is FirstFragmentState.Loading -> {
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                    is FirstFragmentState.DataLoaded -> {
                        viewBinding.progressBar.visibility = View.GONE
                        renderList(it.repo)
                    }
                    is FirstFragmentState.Error -> {
                        viewBinding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun renderList(repos: List<Item>) {
        viewBinding.recyclerView.visibility = View.VISIBLE
        repos.let { listOfRepository -> listOfRepository.let { recyclerAdapter.addData(it) } }
        recyclerAdapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                firstFragmentViewModel.onIntent(FirstFragmentIntent.SearchGitList(query.orEmpty()))
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false
}