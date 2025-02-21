package com.example.durymong.view.mong.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.durymong.R
import com.example.durymong.databinding.ItemHomeChatBinding
import com.example.durymong.model.dto.response.mong.ChatMessage

class ChatHistoryAdapter : RecyclerView.Adapter<ChatHistoryAdapter.ChatViewHolder>() {

    private val chatList = mutableListOf<ChatMessage>()

    fun submitList(newList: List<ChatMessage>) {
        chatList.clear()
        chatList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemHomeChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int = chatList.size

    class ChatViewHolder(private val binding: ItemHomeChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.tvDetailChatDate.text = chatMessage.createdAt  // 날짜 설정
            binding.tvDetailChatQuestion.text = chatMessage.mongQuestion  // 몽의 질문 설정
            binding.tvDetailChatAnswer.text = chatMessage.userAnswer  // 사용자의 답변 설정
        }
    }
}