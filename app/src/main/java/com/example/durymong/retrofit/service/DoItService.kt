package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.request.doit.SubmitTestRequestDto
import com.example.durymong.model.dto.response.doit.ActivityDayRecordResponse
import com.example.durymong.model.dto.response.doit.ActivityRecordResponse
import com.example.durymong.model.dto.response.doit.SubmitTestResponseDto
import retrofit2.Call
import com.example.durymong.model.dto.response.doit.TestMainPageResponseDto
import com.example.durymong.model.dto.response.doit.TestPageResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DoItService {
    @GET("tests/{testId}")
    fun getTestMainPage(@Path("testId") testId: Int): Call<TestMainPageResponseDto>

    @GET("tests/{testId}/questions")  // 위치 다시 해줘야 함
    fun getTestPage(@Path("testId") testId: Int): Call<TestPageResponseDto>

    @POST("tests/{testId}/results")
    fun submitTest(@Path("testId") testId: Int,
                   @Body submitTestRequestDto: SubmitTestRequestDto
    ): Call<SubmitTestResponseDto>


    // 월별 성장일지 조회
    @GET("activities/records")
    fun getActivityRecords(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Call<ActivityRecordResponse>

    // 일별 기록 조회
    @GET("activities/records/{date}")
    fun getActivityDailyRecord(
        @Path("date") date: String // "YYYY-MM-DD" 형식의 날짜
    ): Call<ActivityDayRecordResponse>
}