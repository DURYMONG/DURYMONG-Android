package com.example.durymong.model.dto.response.doit

data class TestPageResponseDto(
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
    val questionList : List<QuestionDto>,
    val requiredTime : Int,

)

data class QuestionDto(
    val number : Int,
    val question: String
)


