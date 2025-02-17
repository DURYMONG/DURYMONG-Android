package com.example.durymong.model.dto.response.settings

data class PasswordResponseDto(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result : String
)