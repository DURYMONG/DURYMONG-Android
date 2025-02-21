package com.example.durymong.model.dto.response.doit

data class UserDailyChatResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: UserDailyMongChatResult
)

data class UserDailyMongChatResult(
    val createdAt: String,
    val mongQuestion: String,
    val userAnswer: String
)
