package com.example.durymong.model.dto.response.column

data class KeywordSearchResponseDto(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: KeywordSearchResult
)

data class KeywordSearchResult(
    val columns: List<KeywordSearchColumn>
)

data class KeywordSearchColumn(
    val categoryId: Int,
    val categoryName: String,
    val preview: String
)
