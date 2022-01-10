/*
package com.example.gitsearch.ui.firstFragment

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.R
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RepoViewHolder(private val itemViewBinding: ItemLayoutBinding) :
    RecyclerView.ViewHolder(itemViewBinding.root) {

    var onItemClick: ((Item) -> Unit)? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(repository: Item) {
        itemView.setOnClickListener {
            repository.let { onItemClick?.invoke(repository) }
            Timber.i("onClick is working!")
        }
        Picasso.get().load(repository.owner.avatar_url).into(itemViewBinding.ivUserAvatar)
        itemViewBinding.tvOwnerName.text = repository.owner.login.plus("/")
        itemViewBinding.tvRepositoryName.text = repository.name
        itemViewBinding.tvRepositoryDescription.text = repository.description
        itemViewBinding.tvProgramLanguage.text = repository.language
        itemViewBinding.tvTags.text =
            repository.topics.toString().substring(1, repository.topics.toString().length - 1);
        itemViewBinding.tvStarCount.text = "\u2606 ${repository.stargazers_count}"
        val updatedDate = repository.updated_at
        val parsedDate = LocalDateTime.parse(updatedDate, DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        itemViewBinding.tvUpdatedAt.text = "Updated $formattedDate"
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
            return RepoViewHolder(view)
        }
    }
}*/
