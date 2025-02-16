package com.example.durymong.model.dto.response.doit

data class ActivityTestListResponse(
    val code: Int,
    val message: String,
    val result: DoItMainPageResult,
    val success: Boolean

)
data class DoItMainPageResult(
    val mongImage: String,
    val mongName: String,
    val activityList: List<ActivityList>
)

data class ActivityList(
    val activityId: Int,
    val activityName: String,
    val checked:Boolean
)
