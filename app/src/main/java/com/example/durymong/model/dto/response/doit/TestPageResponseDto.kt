package com.example.durymong.model.dto.response.doit

class TestPageResponseDto(
    val code : String,
    val message : String,
    val result : TestPageDto,
    val success : Boolean
)

data class TestPageDto(
    val testId : Int,
    val testName: String,
    val numberOfQuestions : Int,
    val numberOfOptions : Int,
    val requiredTime : Int,
    val questions : List<QuestionDto>
)

data class QuestionDto(
    val number : Int,
    val question: String

)


