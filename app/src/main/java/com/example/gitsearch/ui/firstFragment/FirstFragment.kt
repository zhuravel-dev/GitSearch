package com.example.gitsearch.ui.firstFragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentFirstBinding
import com.example.gitsearch.ui.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.fragment_first) {

    private val viewBinding by viewBinding(FragmentFirstBinding::bind)
    private val pagingAdapter by lazy { FirstFragmentAdapter() }
    private val firstFragmentViewModel: FirstFragmentViewModel by viewModels()

    private fun initAdapter() {
        pagingAdapter.onItemClick = {
            firstFragmentViewModel.onIntent(FirstFragmentIntent.SetSelectedRepositoryId(it.id))
            findNavController().navigate(R.id.actionFragmentFirst_to_fragmentDetail)
        }
        Timber.i("In fun init adapter")
        viewBinding.recyclerView.adapter = pagingAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
        initAdapter()
    }

    private fun setupUI() = viewBinding.run {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context, (recyclerView.layoutManager as LinearLayoutManager)
                        .orientation
                )
            )
        }
        recyclerView.adapter = pagingAdapter
        toolbar.customToolbar.setNavigationOnClickListener {
            System.exit(0)
        }
        toolbar.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        firstFragmentViewModel.onIntent(FirstFragmentIntent.SearchGitList(q))
                    }
                }
                return true
            }

            override fun onQueryTextChange(q: String?): Boolean = true
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            firstFragmentViewModel.state.collect {
                when (it) {
                    is FirstFragmentState.Idle -> {
                        viewBinding.tvWelcomeText.visibility = View.VISIBLE
                    }
                    is FirstFragmentState.Loading -> {
                        viewBinding.tvWelcomeText.visibility = View.GONE
                        viewBinding.progressBar.visibility = View.VISIBLE
                    }
                    is FirstFragmentState.DataLoaded -> {
                        viewBinding.tvWelcomeText.visibility = View.GONE
                        viewBinding.progressBar.visibility = View.GONE
                        viewBinding.recyclerView.visibility = View.VISIBLE
                        pagingAdapter.submitData(it.data)
                    }
                    is FirstFragmentState.Error -> {
                        viewBinding.tvWelcomeText.visibility = View.GONE
                        viewBinding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}