package com.example.durymong.model.dto.response.doit

data class ActivityDayRecordResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: ActivityDailyRecord
)

data class ActivityDailyRecord(
    val date: String,
    val mongName: String,
    val mongImage: String
)
