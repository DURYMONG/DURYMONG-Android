package com.example.durymong.model.dto.response.user

data class IdCheckResponse<T>(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: T?
)