package com.example.durymong.retrofit.service

import com.example.durymong.model.dto.response.column.CategoryDetailResponseDto
import com.example.durymong.model.dto.response.column.CategoryResponseDto
import com.example.durymong.model.dto.response.column.ColumnResponseDto
import com.example.durymong.model.dto.response.column.KeywordSearchResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ColumnService {
    // 카테고리 목록 조회
    @GET("column")
    fun getColumnCategories(): Call<CategoryResponseDto>

    // 카테고리 상세 조회
    @GET("columns/categories/{categoryId}/details")
    fun getCategoryDetails(@Path("categoryId") categoryId: String): Call<CategoryDetailResponseDto>

    // 키워드 검색
    @GET("columns/search")
    fun searchColumns(@Query("keyword") keyword: String): Call<KeywordSearchResponseDto>

    // 칼럼 조회
    @GET("columns/categories/{categoryId}")
    fun getColumns(@Path("categoryId") categoryId: String): Call<ColumnResponseDto>
}

