package com.example.gitsearch.ui.firstFragment

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.ItemLayoutBinding
import com.example.gitsearch.ui.extensions.viewBindingVH
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FirstFragmentAdapter : RecyclerView.Adapter<FirstFragmentAdapter.FirstFragmentViewHolder>() {

    private val repositoryList: ArrayList<Item> = arrayListOf()

    class FirstFragmentViewHolder(private val itemViewBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(repository: Item) {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstFragmentViewHolder {
        val reductionViewBinding by parent.viewBindingVH(ItemLayoutBinding::inflate)
        return FirstFragmentViewHolder(reductionViewBinding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FirstFragmentViewHolder, position: Int) {
        holder.bind(repositoryList[position])
    }

    fun addData(list: List<Item>) {
        repositoryList.addAll(list)
    }

    override fun getItemCount(): Int = repositoryList.size
}