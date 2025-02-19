package com.example.durymong.view.do_it.record.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.durymong.databinding.ItemDoItChatbotCardBinding
import com.example.durymong.model.dto.response.doit.BotChatDto

class RVAdapterChatbotHistory(
    private val context: Context,
    private val items: List<BotChatDto>,
    private val onItemClick: (BotChatDto) -> Unit
) : RecyclerView.Adapter<RVAdapterChatbotHistory.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDoItChatbotCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemDoItChatbotCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = items.size
}