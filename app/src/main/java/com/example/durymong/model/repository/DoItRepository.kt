package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.response.doit.TestMainPageResponseDto
import com.example.durymong.model.dto.response.doit.TestPageResponseDto
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.DoItService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoItRepository {
    private val doItService: DoItService = RetrofitObject.createService()

    fun getTestData(
        testId: Int,
        onSuccess: (TestPageResponseDto) -> Unit
    ) {
        doItService.getTestPage(testId).enqueue(object : Callback<TestPageResponseDto> {
            override fun onResponse(
                call: Call<TestPageResponseDto>,
                response: Response<TestPageResponseDto>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                    Log.d("DoItRepository", "onResponseSuccess")

                }
            }
            override fun onFailure(p0: Call<TestPageResponseDto>, p1: Throwable) {
                Log.d("DoItRepository", "onFailure: ${p1.message}")
            }

        })
    }

    fun getTestMainPage(
        testId: Int,
        onSuccess: (TestMainPageResponseDto) -> Unit,
        onError: (Throwable) -> Unit  // 실패 시 처리할 콜백 추가
    ) {
        doItService.getTestMainPage(testId).enqueue(object : Callback<TestMainPageResponseDto> {
            override fun onResponse(
                call: Call<TestMainPageResponseDto>,
                response: Response<TestMainPageResponseDto>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        onSuccess(body)
                        Log.d("DoItRepository", "onResponseSuccess: $body")
                    } else {
                        onError(Throwable("Response body is null"))
                    }
                } else {
                    onError(Throwable("Response error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<TestMainPageResponseDto>, t: Throwable) {
                Log.e("DoItRepository", "onFailure: ${t.message}")
                onError(t) // 실패 시 콜백 실행
            }
        })
    }

}