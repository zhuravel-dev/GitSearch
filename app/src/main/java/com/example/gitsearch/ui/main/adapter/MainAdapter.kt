package com.example.gitsearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitsearch.R
import com.example.gitsearch.data.model.Items
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private val repository: ArrayList<Items>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(repository: Items) {
            itemView.tvRepositoryName.text = repository.name
            itemView.tvRepositoryDescription.text = repository.description
            itemView.tvProgramLanguage.text = repository.language
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.item_layout, parent, false)
        )

    override fun getItemCount(): Int = repository.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(repository[position])

    fun addData(list: List<Items>) {
        repository.addAll(list)
    }

}