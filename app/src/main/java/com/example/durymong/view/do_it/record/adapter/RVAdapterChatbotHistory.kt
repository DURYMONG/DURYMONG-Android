package com.example.durymong.view.do_it.record.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.durymong.databinding.ItemDoItChatbotCardBinding
import com.example.durymong.model.dto.response.doit.BotChatDto

class RVAdapterChatbotHistory(
    private val context: Context,
    private val items: List<BotChatDto>,
    private val onItemClick: (BotChatDto) -> Unit
) : RecyclerView.Adapter<RVAdapterChatbotHistory.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDoItChatbotCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BotChatDto) {
            val requestOptions = RequestOptions()
                .timeout(10000)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 사용
                .override(binding.ivChatbotImg.width, binding.ivChatbotImg.height)
            Glide.with(context)
                .load(item.chatBotImage)
                .apply(requestOptions)
                .into(binding.ivChatbotImg)
            binding.tvChatbotName.text = "${item.description}와의 대화 보기"
            binding.cardChatbotHistory.setOnClickListener {
                onItemClick(item)
            }
        }
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
        val currentItem = items[position]
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int = items.size
}