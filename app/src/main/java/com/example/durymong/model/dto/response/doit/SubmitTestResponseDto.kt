package com.example.durymong.model.dto.response.doit

class SubmitTestResponseDto(
    val code:Int,
    val message: String,
    val result: SubmitResultData,
    val scoreDistributionList: List<ScoreDistributionData>,
    val success: Boolean
)

data class ScoreDistributionData(
    val minScore: Int,
    val maxScore: Int,
    val description: String
)

data class SubmitResultData (
    val minScore: Int,
    val maxScore: Int,
    val description: String
)