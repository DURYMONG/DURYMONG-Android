package com.example.durymong.model.dto.response.doit


data class DiaryResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: DiaryInfo
)

data class DiaryInfo(
    val date: String,
    val content: String,
    val mongImage: String
)
