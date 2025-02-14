package com.example.durymong.model.dto.response.doit

class SubmitTestResponseDto(
    val code:Int,
    val message: String,
    val result: ResponseResultData,
    val success: Boolean
)

data class ResponseResultData (
    val testName: String,
    val userName: String,
    val userScore: Int,
    val userResult: String,
    val scoreDistributionList: String
)