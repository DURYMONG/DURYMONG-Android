package com.example.durymong.model.dto.response.mong

data class ChatHistoryResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: ChatHistoryResult
)

data class ChatHistoryResult(
    val mongImage: String,
    val userChatHistory: List<ChatMessage>
)

data class ChatMessage(
    val conversationId: Int,
    val createdAt: String,
    val mongQuestion: String,
    val userAnswer: String
)