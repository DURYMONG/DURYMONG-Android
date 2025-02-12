package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.response.column.CategoryDetailResponseDto
import com.example.durymong.model.dto.response.column.CategoryResponseDto
import com.example.durymong.model.dto.response.column.ColumnResponseDto
import com.example.durymong.model.dto.response.column.KeywordSearchResponseDto
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.ColumnService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ColumnRepository {
    // 칼럼 관련 기능을 수행하는 Repository
    // 여기에서 함수를 구현해서 call.enque 와 같은 작업 수행
    private val service: ColumnService = RetrofitObject.createService()

    // 카테고리 목록 조회
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
                callback(null)
            }
        })
    }

    // 카테고리 상세 조회
    fun getCategoryDetails(categoryId: String, callback: (CategoryDetailResponseDto?) -> Unit) {
        service.getCategoryDetails(categoryId).enqueue(object : Callback<CategoryDetailResponseDto> {
            override fun onResponse(
                call: Call<CategoryDetailResponseDto>,
                response: Response<CategoryDetailResponseDto>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ColumnRepository", "카테고리 상세 조회 실패: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<CategoryDetailResponseDto>, t: Throwable) {
                Log.e("ColumnRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

    // 키워드 검색
    fun searchColumns(keyword: String, callback: (KeywordSearchResponseDto?) -> Unit) {
        service.searchColumns(keyword).enqueue(object : Callback<KeywordSearchResponseDto> {
            override fun onResponse(
                call: Call<KeywordSearchResponseDto>,
                response: Response<KeywordSearchResponseDto>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ColumnRepository", "키워드 검색 실패: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<KeywordSearchResponseDto>, t: Throwable) {
                Log.e("ColumnRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

    // 칼럼 조회
    fun getColumns(categoryId: String, callback: (ColumnResponseDto?) -> Unit) {
        service.getColumns(categoryId).enqueue(object : Callback<ColumnResponseDto> {
            override fun onResponse(
                call: Call<ColumnResponseDto>,
                response: Response<ColumnResponseDto>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    Log.e("ColumnRepository", "칼럼 조회 실패: ${response.errorBody()?.string()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ColumnResponseDto>, t: Throwable) {
                Log.e("ColumnRepository", "Network error: ${t.message}")
                callback(null)
            }
        })
    }

}