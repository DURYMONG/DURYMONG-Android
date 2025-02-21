package com.example.durymong.model.dto.response.column

data class ColumnResponseDto(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: ColumnResult
)

data class ColumnResult(
    val categoryName: String,
    val title: String,
    val subtitle: String,
    val content: String,
    val image: String
)
