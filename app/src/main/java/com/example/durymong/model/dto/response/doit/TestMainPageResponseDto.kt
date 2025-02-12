package com.example.durymong.model.dto.response.doit

class TestMainPageResponseDto(
    val code : String,
    val message : String,
    val result : TestMainPageDto,
    val success : Boolean
)

data class TestMainPageDto(
    val testId : Int,
    val testName : String,
    val testEnglishName: String,
    val content : String,
    val evaluationInfo : String,
    val countOfQuestions : Int,
    val requiredTime: Int,
    val lastTestDto: LastTestDto
)

data class LastTestDto(
    val date : String,
    val userName : String,
    val lastScore : Int
)