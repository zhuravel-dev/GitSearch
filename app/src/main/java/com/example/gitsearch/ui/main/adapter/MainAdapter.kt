package com.example.gitsearch.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.data.model.Item
import com.example.gitsearch.databinding.ItemLayoutBinding
import com.example.gitsearch.ui.main.extensions.viewBindingVH

class MainAdapter(
    private val repository: ArrayList<Item>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(private val itemViewBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(repository: Item) = itemViewBinding.run {
            tvRepositoryName.text = repository.name
            tvRepositoryDescription.text = repository.description
            tvProgramLanguage.text = repository.language
        }
    }

    override fun getItemCount(): Int = repository.size

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