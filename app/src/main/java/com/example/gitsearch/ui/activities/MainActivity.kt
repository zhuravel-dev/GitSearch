package com.example.gitsearch.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.example.gitsearch.databinding.ActivityMainBinding
import com.example.gitsearch.ui.extensions.viewBinding
import com.example.gitsearch.ui.firstFragment.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val toolbar = viewBinding.toolbarActivity
        setSupportActionBar(toolbar)
        tabLayout = viewBinding.tabLayout

        val pager = viewBinding.viewPager
        val adapter = ViewPagerAdapter(this)
        pager.adapter = adapter

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Sort by Stars"
                }
                1 -> {
                    tab.text = "Sort by"
                }
            }
        }.attach()

        // toolbar.customToolbar.setNavigationOnClickListener { exitProcess(0) }
    }
}