package com.example.gitsearch.ui.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gitsearch.ui.firstFragment.FragmentSortingByStars
import com.example.gitsearch.ui.firstFragment.FragmentSortingByUpdate
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
class ViewPagerAdapter(
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> FragmentSortingByStars()
            1 -> FragmentSortingByUpdate()
            else -> {
                Fragment()
            }
        }
    }
}