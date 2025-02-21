package com.example.durymong.model.dto.response.settings

data class UserEliminationResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: UserEliminationResult?
)

data class UserEliminationResult(
    val mongName: String,
    val id: String,
    val withMongDate: Int,
    val mongImage: String
)
