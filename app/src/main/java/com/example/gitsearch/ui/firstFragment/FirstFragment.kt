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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.R
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.FragmentFirstBinding
import com.example.gitsearch.ui.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FirstFragment : Fragment(R.layout.fragment_first), SearchView.OnQueryTextListener {

    private val viewBinding by viewBinding(FragmentFirstBinding::bind)
    private val recyclerAdapter by lazy { FirstFragmentAdapter() }
    private val firstFragmentViewModel: FirstFragmentViewModel by viewModels()
    //private var after: String? = null
    private var offset: String? = null
    private var lastFetchedPosition: Int = 0

    private fun initAdapter() {
        recyclerAdapter.onItemClick = {
            firstFragmentViewModel.onIntent(FirstFragmentIntent.SetSelectedRepositoryId(it.id))
            findNavController().navigate(R.id.actionFragmentFirst_to_fragmentDetail)
        }
        Timber.i("In fun init adapter")
        viewBinding.recyclerView.adapter = recyclerAdapter

        viewBinding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as? LinearLayoutManager
                val lastPosition = manager?.findLastCompletelyVisibleItemPosition()?:0
                val lastPlusOne = lastPosition + 1
                if(lastPlusOne%DEFAULT_PAGE_SIZE == 0 && lastPlusOne > lastFetchedPosition) {
                    firstFragmentViewModel.onIntent(FirstFragmentIntent.FetchGitList)
                   //firstFragmentViewModel.onIntent(FirstFragmentIntent.SearchGitList)
                    lastFetchedPosition = lastPlusOne
                }
            }
        })

        /*viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!viewBinding.recyclerView.canScrollVertically(1)) {
                    Toast.makeText(context, "working", Toast.LENGTH_SHORT).show()

                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })*/
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                firstFragmentViewModel.onIntent(FirstFragmentIntent.FetchGitList)
            }
        }
    }*/

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
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = recyclerAdapter
        toolbar.customToolbar.setNavigationOnClickListener {
            System.exit(0)
        }
        toolbar.searchView.setOnQueryTextListener(this@FirstFragment)
        toolbar.searchView.queryHint = getString(R.string.hint_search)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            firstFragmentViewModel.stateFirst.collect {
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
                        renderList(it.repo)
                    }
                    is FirstFragmentState.Error -> {
                        viewBinding.tvWelcomeText.visibility = View.GONE
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

    override fun onQueryTextSubmit(q: String): Boolean {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                firstFragmentViewModel.onIntent(FirstFragmentIntent.SearchGitList(q, 1, 30))
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    companion object {
        const val DEFAULT_PAGE_SIZE = 30
    }
}