package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.response.mong.ChatHistoryResponse
import com.example.durymong.model.dto.response.mong.HomeResponse
import retrofit2.Call
import retrofit2.http.GET

interface MongService {

    @GET("users/home")
    fun getHomeData() : Call<HomeResponse>

    @GET("users/mong-chat-history")
    fun getChatHistory(): Call<ChatHistoryResponse>
}