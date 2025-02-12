package com.example.durymong.model.dto.response.column

data class CategoryDetailResponseDto(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: ColumnResult // ColumnResponseDto의 ColumnResult와 동일
)
