package com.example.gitsearch.ui.pager

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.R
import com.example.gitsearch.databinding.FragmentMainPagerBinding
import com.example.gitsearch.ui.extensions.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.exitProcess

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
class MainPagerFragment : Fragment(R.layout.fragment_main_pager) {

    private val viewBinding: FragmentMainPagerBinding? by viewBinding(
        FragmentMainPagerBinding::bind)

    private lateinit var mainTabLayout: TabLayout
    private val pagerSharedViewModel: PagerSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = viewBinding?.tbPagerFragment
        toolbar?.setNavigationOnClickListener { exitProcess(0) }
        setupUI()
        setupPager()
    }

    private fun setupUI() {
        val searchView = viewBinding?.searchView
        mainTabLayout = viewBinding!!.tabLayout

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        when (mainTabLayout.selectedTabPosition) {
                            0 -> {
                                pagerSharedViewModel.onIntent(PagerIntent.SearchGitListSortedByStars(q))
                            }
                            1 -> {
                                pagerSharedViewModel.onIntent(
                                    PagerIntent.SearchGitListSortedByUpdate(q))
                            }
                            else -> {
                                Timber.i("MainPagerFragment, searchView.setOnQueryTextListener - something wrong")
                            }
                        }}
                }
                return true
            }
            override fun onQueryTextChange(q: String?): Boolean = true
        })
    }

    private fun setupPager() {
        val pager = viewBinding!!.viewPager
        val adapter = ViewPagerAdapter(requireActivity())
        pager.adapter = adapter

        TabLayoutMediator(mainTabLayout, pager) { tab, position ->
            when (position) {
                0 -> { tab.text = "Sort by Stars" }
                1 -> { tab.text = "Sort by Update"}
            }
        }.attach()
    }
}