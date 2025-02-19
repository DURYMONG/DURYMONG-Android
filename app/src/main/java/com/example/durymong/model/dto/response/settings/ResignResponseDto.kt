package com.example.durymong.model.dto.response.settings

data class ResignResponseDto(
    val success : Boolean,
    val code : Int,
    val message : String,
    val result : UserInfo
)

//TODO : sealed class로 바꾸기

data class UserInfo (
    val id : String,
    val withMongDate : Int,
    val mongImage : String
)
