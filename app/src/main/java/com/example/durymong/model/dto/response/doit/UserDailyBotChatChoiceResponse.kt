package com.example.durymong.model.dto.response.doit

data class UserDailyBotChatChoiceResponse(
    val code: Int,
    val message: String,
    val success: Boolean,
    val result: UserDailyBotChatChoiceResult
)

data class UserDailyBotChatChoiceResult(
    val targetDate: String,
    val chatBotChoiceDtos: List<BotChatDto>
)

data class BotChatDto(
    val chatBotId: Int,
    val chatBotImage: String,
    val description: String
)
