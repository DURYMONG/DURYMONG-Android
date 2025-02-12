package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.doit.SubmitTestRequestDto
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

    @GET("tests/{testId}/questions")  // 위치 다시 해줘야 함
    fun getTestPage(@Path("testId") testId: Int): Call<TestPageResponseDto>

    @POST("tests/{testId}/results")
    fun submitTest(@Path("testId") testId: Int,
                   @Body submitTestRequestDto: SubmitTestRequestDto
    ): Call<SubmitTestResponseDto>

}