package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.response.chat.ChatBotResponse
import com.example.durymong.model.dto.response.chat.ChatStartResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {
    @GET("users/chatbot-list")
    fun getChatBots(): Call<ChatBotResponse>

    @GET("/chatbots/chat-start/{chatBotId}")
    fun startChat(
        @Path("chatBotId") chatBotId: Int
    ): Call<ChatStartResponse>
}