package com.example.gitsearch.ui.firstFragment

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.ItemLayoutBinding
import com.example.gitsearch.ui.extensions.viewBindingVH
import com.squareup.picasso.Picasso
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
class FirstFragmentAdapter :
    PagingDataAdapter<Item, RecyclerView.ViewHolder>(ArticleDiffItemCallback) {

    var onItemClick: ((Item) -> Unit)? = null

    inner class RepoViewHolder(private val itemViewBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: Item) {
            itemView.setOnClickListener {
                data.let { onItemClick?.invoke(data) }
            }
            Picasso.get().load(data.owner.avatar_url).into(itemViewBinding.ivUserAvatar)
            itemViewBinding.tvOwnerName.text = data.owner.login.plus("/")
            itemViewBinding.tvRepositoryName.text = data.name
            itemViewBinding.tvRepositoryDescription.text = data.description
            itemViewBinding.tvProgramLanguage.text = data.language
            itemViewBinding.tvTags.text =
                data.topics.toString().substring(1, data.topics.toString().length - 1);
            itemViewBinding.tvStarCount.text = "\u2606 ${data.stargazers_count}"
            val updatedDate = data.updated_at
            val parsedDate = LocalDateTime.parse(updatedDate, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            itemViewBinding.tvUpdatedAt.text = "Updated $formattedDate"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val reductionViewBinding by parent.viewBindingVH(ItemLayoutBinding::inflate)
        return RepoViewHolder(reductionViewBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepoViewHolder) {
            getItem(position)?.let { holder.bind(it) }
        }
    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name && oldItem.url == newItem.url
    }
}