package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.response.chat.ChatBot
import com.example.durymong.model.dto.response.chat.ChatBotResponse
import com.example.durymong.model.dto.response.chat.ChatStartResponse
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.ChatService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class ChatRepository {
    // 채팅봇 관련 기능을 수행하는 Repository
    // 여기에서 함수를 구현해서 call.enque 와 같은 작업 수행

    private val service: ChatService = RetrofitObject.createService()

    fun getChatBots(callback: (List<ChatBot>?) -> Unit) {
        service.getChatBots().enqueue(object : Callback<ChatBotResponse> {
            override fun onResponse(call: Call<ChatBotResponse>, response: Response<ChatBotResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.result)
                } else {
                    Log.e("ChatBotRepository", "조회 실패: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ChatBotResponse>, t: Throwable) {
                Log.e("ChatBotRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

    fun startChat(chatBotId: Int, callback: (ChatStartResponse?) -> Unit) {
        service.startChat(chatBotId).enqueue(object : Callback<ChatStartResponse> {
            override fun onResponse(call: Call<ChatStartResponse>, response: Response<ChatStartResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ChatRepository", "Failed: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ChatStartResponse>, t: Throwable) {
                Log.e("ChatRepository", "Network Error: ${t.message}")
                callback(null)
            }
        })
    }
}