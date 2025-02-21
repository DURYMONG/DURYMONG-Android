package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.settings.PasswordRequestDto
import com.example.durymong.model.dto.request.settings.UserInfoRequestDto
import com.example.durymong.model.dto.response.settings.DeleteHistoryResponse
import com.example.durymong.model.dto.response.settings.PasswordResponseDto
import com.example.durymong.model.dto.response.settings.ResignResponseDto
import com.example.durymong.model.dto.response.settings.UserEliminationResponse
import com.example.durymong.model.dto.response.settings.UserInfoResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SettingService {
    @POST("users/password-modification")
    fun postPasswordModification(
        @Body request: PasswordRequestDto
    ): Call<PasswordResponseDto>

    @POST("users/info-modification")
    fun postInfoModification(
        @Body request: UserInfoRequestDto
    ) : Call<UserInfoResponseDto>

    @GET("users/user-elimination")
    fun getUserElimination(): Call<ResignResponseDto>

    @POST("/users/history-deletion")
    fun deleteHistory(): Call<DeleteHistoryResponse>

    @GET("/users/user-elimination")
    fun getUserEliminationInfo(): Call<UserEliminationResponse>
}