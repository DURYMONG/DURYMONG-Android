package com.example.durymong.model.dto.response.chat

data class ChatBotResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: List<ChatBot>
)

data class ChatBot(
    val chatBotId: Int,
    val name: String,
    val mbti: String,
    val accent: String,
    val nickname: String,
    val slogan: String,
    val image: String
)
