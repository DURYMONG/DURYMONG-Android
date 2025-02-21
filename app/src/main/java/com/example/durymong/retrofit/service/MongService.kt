package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.mong.MongCreateRequest
import com.example.durymong.model.dto.response.mong.ChatHistoryResponse
import com.example.durymong.model.dto.response.mong.HomeResponse
import com.example.durymong.model.dto.response.user.ApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MongService {

    @GET("users/home")
    fun getHomeData() : Call<HomeResponse>

    @GET("users/mong-chat-history")
    fun getChatHistory(): Call<ChatHistoryResponse>

    @POST("mongs/creation")
    suspend fun createMong(@Body request: MongCreateRequest): Response<ApiResponse<String>>
}