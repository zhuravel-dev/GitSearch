package com.example.gitsearch.ui.firstFragment

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.data.local.model.ItemLocalModel
import com.example.gitsearch.databinding.ItemFirstLayoutBinding
import com.example.gitsearch.ui.extensions.viewBindingVH
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@ExperimentalCoroutinesApi
class FirstFragmentAdapter :
    PagingDataAdapter<ItemLocalModel, RecyclerView.ViewHolder>(ArticleDiffItemCallback) {

    var onItemClick: ((ItemLocalModel) -> Unit)? = null

    inner class RepoViewHolder(private val itemViewBinding: ItemFirstLayoutBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(data: ItemLocalModel) {
            itemView.setOnClickListener {
                data.let { onItemClick?.invoke(data) }
            }
            Picasso.get().load(data.owner?.avatar_url)
                .transform(CropCircleTransformation()).into(itemViewBinding.ivUserAvatar)
            itemViewBinding.tvOwnerName.text = data.owner?.login.plus("/")
            itemViewBinding.tvRepositoryName.text = data.name
            itemViewBinding.tvRepositoryDescription.text = data.description
            itemViewBinding.tvProgramLanguage.text = data.language
            itemViewBinding.tvTags.text =
                data.topics.toString().substring(1, data.topics.toString().length - 1);
            itemViewBinding.tvStarCount.text = "\u2606 ${data.stargazers_count}"
            val updatedDate = data.updated_at
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val parsedDate = LocalDateTime.parse(updatedDate, DateTimeFormatter.ISO_DATE_TIME)
                val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                itemViewBinding.tvUpdatedAt.text = "Updated $formattedDate"
            } else {
                val SDFormat = SimpleDateFormat("dd.MM.yyyy")
                val formattingDate = SDFormat.format(Date())
                itemViewBinding.tvUpdatedAt.text = "Updated $formattingDate"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val reductionViewBinding by parent.viewBindingVH(ItemFirstLayoutBinding::inflate)
        return RepoViewHolder(reductionViewBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RepoViewHolder) {
            getItem(position)?.let { holder.bind(it) }
        }
    }
}

private object ArticleDiffItemCallback : DiffUtil.ItemCallback<ItemLocalModel>() {

    override fun areItemsTheSame(oldItem: ItemLocalModel, newItem: ItemLocalModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ItemLocalModel, newItem: ItemLocalModel): Boolean {
        return oldItem.name == newItem.name && oldItem.url == newItem.url
    }
}