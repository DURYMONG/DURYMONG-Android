package com.example.durymong.model.dto.response.doit

class DoItResponse (
    val code : Int,
    val message : String,
    val result : List<DoItResult>
)
data class DoItResult(
    val mongImage: String,
    val mongName: String,

)
