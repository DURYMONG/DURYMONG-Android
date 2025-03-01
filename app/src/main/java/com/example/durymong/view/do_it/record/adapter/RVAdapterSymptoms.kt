package com.example.durymong.view.do_it.record.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.durymong.databinding.ItemDoitChatbotSymptomBinding

class RVAdapterSymptoms(
    private val context: Context,
    private val items: List<String>,
) : RecyclerView.Adapter<RVAdapterSymptoms.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDoitChatbotSymptomBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: String){
                    binding.tvSymptom.text = item
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDoitChatbotSymptomBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        if (currentItem != null){
            holder.bind(currentItem)
        }
    }
}