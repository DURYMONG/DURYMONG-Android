package com.example.durymong.model.dto.response.user

data class RegisterResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: String
)