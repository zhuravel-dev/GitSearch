package com.example.gitsearch.ui.firstFragment

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.ui.firstFragment.RepoViewHolder.Companion.create

class ReposAdapter: PagingDataAdapter<Item, RepoViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return FirstFragmentAdapter.FirstFragmentViewHolder.create(parent)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}