package com.example.gitsearch.ui.main.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.R
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.ItemLayoutBinding
import com.example.gitsearch.ui.main.extensions.viewBindingVH
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainAdapter(
    private val repository: ArrayList<Item>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(private val itemViewBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(repository: Item) = itemViewBinding.run {
            tvOwnerName.text = repository.owner.login.plus("/")
            tvRepositoryName.text = repository.name
            tvRepositoryDescription.text = repository.description
            tvProgramLanguage.text = repository.language
            tvTags.text =
                repository.topics.toString().substring(1, repository.topics.toString().length - 1);
            tvStarCount.text = "\u2606 ${repository.stargazers_count}"
            val updatedDate = repository.updated_at
            val parsedDate = LocalDateTime.parse(updatedDate, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            tvUpdatedAt.text = "Updated on $formattedDate"
        }
    }
    override fun getItemCount(): Int = repository.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(repository[position])

    fun addData(list: List<Item>) {
        repository.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val reductionViewBinding by parent.viewBindingVH(ItemLayoutBinding::inflate)
        return DataViewHolder(reductionViewBinding)
    }

}