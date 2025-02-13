package com.example.durymong.view.column.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.durymong.databinding.ItemColumnSearchResultBinding
import com.example.durymong.model.dto.response.column.KeywordSearchColumn

class RVAdapterColumnSearchResult(
    private val context: Context,
    private val items: List<KeywordSearchColumn>,
    private val onItemClick: (KeywordSearchColumn) -> Unit
) : RecyclerView.Adapter<RVAdapterColumnSearchResult.ViewHolder>() {
    inner class ViewHolder(val binding: ItemColumnSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeywordSearchColumn, position: Int) {
            binding.columnKeyword.text = item.categoryName
            binding.columnPreview.text = item.preview
            binding.cardColumnSearchResult.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVAdapterColumnSearchResult.ViewHolder {
        val binding = ItemColumnSearchResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }


}