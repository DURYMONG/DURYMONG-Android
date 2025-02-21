package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.user.ReIssueTokenRequest
import com.example.durymong.model.dto.request.user.RegisterRequest
import com.example.durymong.model.dto.request.user.UserLoginRequestDto
import com.example.durymong.model.dto.response.user.ApiResponse
import com.example.durymong.model.dto.response.user.IdCheckResponse
import com.example.durymong.model.dto.response.user.PasswordCheckResponse
import com.example.durymong.model.dto.response.user.ReIssueTokenResponse
import com.example.durymong.model.dto.response.user.RegisterResponse
import com.example.durymong.model.dto.response.user.UserTokenRequestDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @POST("auth/login")
    fun postLogin(
        @Body userData: UserLoginRequestDto
    ): Call<UserTokenRequestDto>

    @POST("auth/newtokens")
    fun postNewTokens(
        @Body tokenRequest: ReIssueTokenRequest
    ): Call<ReIssueTokenResponse>

    @GET("/users/userid/{userId}")
    suspend fun checkUserId(@Path("userId") userId: String): IdCheckResponse<String>

    @GET("/users/email/{email}")
    suspend fun checkEmailDuplicate(@Path("email") email: String): ApiResponse<String>

    @POST("/users/email-requests")
    suspend fun requestEmailAuthCode(@Query("email") email: String): ApiResponse<String>

    @GET("/users/email-verification/{email}/{authCode}")
    suspend fun verifyEmailAuthCode(
        @Path("email") email: String,
        @Path("authCode") authCode: String
    ): ApiResponse<String>

    @GET("/users/password/{password}")
    suspend fun checkPassword(
        @Path("password") password: String
    ): Response<PasswordCheckResponse>

    @POST("/users/signup")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>

}