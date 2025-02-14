package com.example.durymong.model.dto.response.doit

data class ActivityRecordResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val result: MonthInfo
)

data class MonthInfo(
    val nickname: String,
    val month: Int,
    val dayCountList: List<DateInfo>
)

data class DateInfo(
    val date: String,
    val count: Int
)
