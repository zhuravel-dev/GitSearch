package com.example.gitsearch.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.databinding.ActivityMainBinding
import com.example.gitsearch.ui.extensions.viewBinding
import com.example.gitsearch.ui.firstFragment.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.exitProcess

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var mainTabLayout: TabLayout
    private val mainSharedViewModel: MainSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val toolbar = viewBinding.toolbarActivity
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { exitProcess(0) }
        val searchView = viewBinding.searchView
        mainTabLayout = viewBinding.tabLayout

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String): Boolean {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        when (mainTabLayout.selectedTabPosition) {
                            0 -> {
                                mainSharedViewModel.onIntent(MainIntent.SearchGitListSortedByStars(q))
                            }
                            1 -> {
                                mainSharedViewModel.onIntent(MainIntent.SearchGitListSortedByUpdate(q))
                            }
                            else -> {
                                Timber.i("MainActivity, searchView.setOnQueryTextListener - something wrong")
                            }
                        }
                    }
                }
                return true
            }
            override fun onQueryTextChange(q: String?): Boolean = true
        })

        val pager = viewBinding.viewPager
        val adapter = ViewPagerAdapter(this)
        pager.adapter = adapter


        TabLayoutMediator(mainTabLayout, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Sort by Stars"
                }
                1 -> {
                    tab.text = "Sort by Update"
                }
            }
        }.attach()
    }
}