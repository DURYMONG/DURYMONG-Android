package com.example.durymong.model.dto.response.column

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val code: Int,
    val message: String,
    val result : List<Category>
)

@Serializable
data class Category(
    val name: String,
    val image: String
)
