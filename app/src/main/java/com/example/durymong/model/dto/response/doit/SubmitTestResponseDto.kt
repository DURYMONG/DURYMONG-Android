package com.example.durymong.model.dto.response.doit

data class SubmitTestResponseDto(
    val code:Int,
    val message: String,
    val result: ResponseResultData,
    val success: Boolean
)

data class ResponseResultData (
    val testName: String,
    val nickName: String,
    val userScore: Int,
    val userResult: String,
    val scoreDistributionList: String
)