package com.example.durymong.model.dto.response.doit

data class UserDailyBotChatResponse(
    val code: Int,
    val message: String,
    val result: UserDailyBotChatResult,
    val success: Boolean
)

data class UserDailyBotChatResult(
    val targetDate: String,
    val chatBotImage: String,
    val chatHistory: List<ChatBotHistory>
)

data class ChatBotHistory(
    val startTime: String,
    val greetingMessage: String,
    val symptoms: List<String>,
    val additionalSymptom: String,
    val botPredictionMessage: String,
    val testRecommendationMessage: String,
    val recommendedTests: List<String>,
    val diaryRecommendationMessage: String,
    val finalMessage: String
)
