package com.example.durymong.model.dto.response.chat

data class ChatStartResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: ChatStartResult
)

data class ChatStartResult(
    val chatBotImage: String,
    val message: String,
    val chatSessionId: Int
)
