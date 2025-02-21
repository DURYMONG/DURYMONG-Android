package com.example.durymong.model.dto.response.column


data class CategoryResponseDto(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result : CategoryResult
)

data class CategoryResult(
    val categories: List<Category> // categories 배열을 담는 객체
)

data class Category(
    val categoryId: Int,
    val name: String,
    val image: String,
    val detail: String
)
