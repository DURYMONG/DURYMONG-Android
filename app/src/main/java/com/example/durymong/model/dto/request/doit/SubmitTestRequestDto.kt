package com.example.durymong.model.dto.request.doit

class SubmitTestRequestDto(
    var responseList: List<TestPageResponseData>
)

data class TestPageResponseData(
    val number: Int,
    val choice: Int
)



