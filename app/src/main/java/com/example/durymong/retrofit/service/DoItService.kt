package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.doit.CheckActivityRequest
import com.example.durymong.model.dto.request.doit.SubmitTestRequestDto
import com.example.durymong.model.dto.response.doit.ActivityTestListResponse
import com.example.durymong.model.dto.response.doit.DeactivationResponse
import com.example.durymong.model.dto.response.doit.SubmitTestResponseDto
import retrofit2.Call
import com.example.durymong.model.dto.response.doit.TestMainPageResponseDto
import com.example.durymong.model.dto.response.doit.TestPageResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DoItService {
    @GET("tests/{testId}")
    fun getTestMainPage(@Path("testId") testId: Int): Call<TestMainPageResponseDto>

    @GET("tests/{testId}/questions")
    fun getTestPage(
        @Path("testId") testId: Int
    ): Call<TestPageResponseDto>

    @POST("tests/{testId}/results")
    fun submitTest(@Path("testId") testId: Int,
                   @Body submitTestRequestDto: SubmitTestRequestDto
    ): Call<SubmitTestResponseDto>

    @GET("activities")
    fun getDoItMainPage(): Call<ActivityTestListResponse>

    @POST("activities/user-records")
    fun submitCheck(@Body checkActivityRequest: CheckActivityRequest): Call<Void>

    @POST("activities/user-records/{activityId}/deactivation")
    fun cancelCheck(@Path("activityId") activityId: Int): Call<DeactivationResponse>
}