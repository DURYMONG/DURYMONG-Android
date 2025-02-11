package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.response.column.CategoryResponseDto
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.ColumnService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColumnRepository {
    // 칼럼 관련 기능을 수행하는 Repository
    // 여기에서 함수를 구현해서 call.enque 와 같은 작업 수행
    private val service: ColumnService = RetrofitObject.createService()

    fun getColumnCategories(callback: (CategoryResponseDto?) -> Unit) {
        service.getColumnCategories().enqueue(object : Callback<CategoryResponseDto> {
            override fun onResponse(
                call: Call<CategoryResponseDto>,
                response: Response<CategoryResponseDto>
            ) {
                if (response.isSuccessful) {
                    Log.d("successful", "카테고리 수정 성공: ")
                    callback(response.body())
                } else {
                    Log.d("fail", "카테고리 수정 실패: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CategoryResponseDto>, t: Throwable) {
                Log.d("fail", "네트워크 요청 실패: ${t.message}")
            }
        })
    }

}