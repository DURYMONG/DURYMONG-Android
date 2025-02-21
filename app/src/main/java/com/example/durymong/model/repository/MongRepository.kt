package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.request.mong.MongCreateRequest
import com.example.durymong.model.dto.response.mong.ChatHistoryResponse
import com.example.durymong.model.dto.response.mong.ChatHistoryResult
import com.example.durymong.model.dto.response.mong.HomeResponse
import com.example.durymong.model.dto.response.user.ApiResponse
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.MongService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MongRepository {
    // 몽(홈) 관련 기능을 수행하는 Repository
    // 여기에서 함수를 구현해서 call.enque 와 같은 작업 수행


    private val service: MongService = RetrofitObject.createService()

    fun getHomeData(callback: (HomeResponse?) -> Unit) {
        service.getHomeData().enqueue(object : Callback<HomeResponse> {
            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ColumnRepository", "조회 실패: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                Log.e("", "Network error: ${t.message}")
                callback(null)
            }
        })

    }

    fun getChatHistory(callback: (ChatHistoryResult?) -> Unit) {
        service.getChatHistory().enqueue(object : Callback<ChatHistoryResponse> {
            override fun onResponse(
                call: Call<ChatHistoryResponse>,
                response: Response<ChatHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()?.result)
                } else {
                    Log.e("ChatHistoryRepository", "조회 실패: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ChatHistoryResponse>, t: Throwable) {
                Log.e("ChatHistoryRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

    suspend fun createMong(request: MongCreateRequest): ApiResponse<String> {
        return try {
            val response = service.createMong(request)

            if (response.isSuccessful) {
                response.body() ?: ApiResponse(false, 500, "서버 응답 없음", null)
            } else {
                ApiResponse(false, response.code(), "몽 생성 실패", null)
            }
        } catch (e: HttpException) {
            Log.e("MongRepository", "HTTP 오류: ${e.message}")
            ApiResponse(false, 500, "서버 오류 발생", null)
        } catch (e: Exception) {
            Log.e("MongRepository", "네트워크 오류: ${e.message}")
            ApiResponse(false, 500, "네트워크 오류 발생", null)
        }
    }
}