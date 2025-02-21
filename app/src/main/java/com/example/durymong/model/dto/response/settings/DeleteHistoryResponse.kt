package com.example.durymong.model.dto.response.settings

data class DeleteHistoryResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: String
)
