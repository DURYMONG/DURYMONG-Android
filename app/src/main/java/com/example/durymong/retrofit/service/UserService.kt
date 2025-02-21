package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.user.ReIssueTokenRequest
import com.example.durymong.model.dto.request.user.UserLoginRequestDto
import com.example.durymong.model.dto.response.user.ReIssueTokenResponse
import com.example.durymong.model.dto.response.user.UserTokenRequestDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("auth/login")
    fun postLogin(
        @Body userData: UserLoginRequestDto
    ): Call<UserTokenRequestDto>

    @POST("auth/newtokens")
    fun postNewTokens(
        @Body tokenRequest: ReIssueTokenRequest
    ): Call<ReIssueTokenResponse>
}