package com.example.durymong.model.dto.response.mong


data class HomeResponse(
    val success : Boolean,
    val code : Int,
    val message : String,
    val result : HomeData
)

data class HomeData (
    val date : String,
    val withMongDate : Int,
    val mongName : String,
    val mongImage: String,
    val mongQuestion : String,
    val userAnswer : String
)
