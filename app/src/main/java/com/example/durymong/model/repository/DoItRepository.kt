package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.request.doit.CheckActivityRequest
import com.example.durymong.model.dto.request.doit.SubmitTestRequestDto
import com.example.durymong.model.dto.request.doit.UserDailyBotChatChoiceRequest
import com.example.durymong.model.dto.request.doit.UserDailyBotChatRequest
import com.example.durymong.model.dto.request.doit.UserDailyChatRequest
import com.example.durymong.model.dto.request.doit.WriteDiaryReq
import com.example.durymong.model.dto.response.doit.ActivityDayRecordResponse
import com.example.durymong.model.dto.response.doit.ActivityRecordResponse
import com.example.durymong.model.dto.response.doit.ActivityTestListResponse
import com.example.durymong.model.dto.response.doit.DeactivationResponse
import com.example.durymong.model.dto.response.doit.DiaryResponse
import com.example.durymong.model.dto.response.doit.SubmitTestResponseDto
import com.example.durymong.model.dto.response.doit.TestMainPageResponseDto
import com.example.durymong.model.dto.response.doit.TestPageResponseDto
import com.example.durymong.model.dto.response.doit.UserDailyBotChatChoiceResponse
import com.example.durymong.model.dto.response.doit.UserDailyBotChatResponse
import com.example.durymong.model.dto.response.doit.UserDailyChatResponse
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.DoItService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoItRepository {
    private val doItService: DoItService = RetrofitObject.createService()

    fun getTestData(testId: Int, onSuccess: (TestPageResponseDto) -> Unit)=
        doItService.getTestPage(testId).enqueue(object : Callback<TestPageResponseDto> {
            override fun onResponse(
                p0: Call<TestPageResponseDto>,
                p1: Response<TestPageResponseDto>
            ) {
                if (p1.isSuccessful) {
                    val body = p1.body()
                    if (body != null) {
                        onSuccess(body)
                        Log.d("DoItRepository", body.result.numberOfOptions.toString())
                    }
                }
            }

            override fun onFailure(p0: Call<TestPageResponseDto>, p1: Throwable) {
                Log.d("testData", "onFailure: ${p1.message}")
            }


        })


    fun getTestMainPage(testId: Int,
                        onSuccess: (TestMainPageResponseDto) -> Unit
    ) =
        doItService.getTestMainPage(testId).enqueue(object : Callback<TestMainPageResponseDto> {
            override fun onResponse(
                call: Call<TestMainPageResponseDto>,
                response: Response<TestMainPageResponseDto>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        onSuccess(body)
                    }
                }
            }

            override fun onFailure(call: Call<TestMainPageResponseDto>, t: Throwable) {
                Log.e("getTestMainPage", "onFailure: ${t.message}")
            }
        })

    fun cancelCheck(activityId: Int) {
        doItService.cancelCheck(activityId).enqueue(object : Callback<DeactivationResponse>{
            override fun onResponse(
                p0: Call<DeactivationResponse>,
                p1: Response<DeactivationResponse>
            ) {
                if(p1.isSuccessful){
                    if(p1.body()!=null){
                        Log.d("cancelCheck", "onResponseSuccess")
                    }
                }
            }

            override fun onFailure(p0: Call<DeactivationResponse>, p1: Throwable) {
                Log.d("cancelCheck", "onFailure: ${p1.message}")
            }

        }
        )
    }

    fun getTestResult(testId: Int, submitTestRequestDto: SubmitTestRequestDto,onSuccess: (SubmitTestResponseDto) -> Unit)=
        doItService.submitTest(testId, submitTestRequestDto).enqueue(object : Callback<SubmitTestResponseDto> {
            override fun onResponse(
                p0: Call<SubmitTestResponseDto>,
                response: Response<SubmitTestResponseDto>
            ) {
                if(response.isSuccessful){
                    if(response.body()!=null){
                        onSuccess(response.body()!!)
                    }else{
                        Log.d("getTestResult", "resultNull")
                    }
                }
                else{
                    Log.d("getTestResult", "onResponseFail")
                }

            }

            override fun onFailure(p0: Call<SubmitTestResponseDto>, p1: Throwable) {
                Log.d("getTestResult", "onFailure: ${p1.message}")
            }


        })

        fun getDoItMainPage(onSuccess: (ActivityTestListResponse) -> Unit) {
            doItService.getDoItMainPage().enqueue(object : Callback<ActivityTestListResponse> {
                override fun onResponse(
                    p0: Call<ActivityTestListResponse>,
                    p1: Response<ActivityTestListResponse>
                ) {
                    if(p1.isSuccessful){
                        if(p1.body()!=null){
                            onSuccess(p1.body()!!)
                        }else{
                            Log.d("DoItMainPage", "resultNull")
                        }
                    }
                }
                override fun onFailure(p0: Call<ActivityTestListResponse>, p1: Throwable) {
                    Log.d("DoItMainPage", "onFailure: ${p1.message}")
                }
            })
        }
    fun submitCheck(checkActivityRequest: CheckActivityRequest) {
        doItService.submitCheck(checkActivityRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("doItCheck", "체크 성공")
                } else {
                    Log.e("doItCheck", "오류 코드: ${response.code()}, 메시지: ${response.errorBody()?.string()}")
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("doItCheck", "요청 실패: ${t.message}")
            }
        })
    }

    // 월별 성장일지 조회
    fun getMonthlyRecord(
        year: Int,
        month: Int,
        callback: (ActivityRecordResponse?) -> Unit
    ) {
        doItService.getActivityRecords(year, month).enqueue(object : Callback<ActivityRecordResponse> {
            override fun onResponse(
                call: Call<ActivityRecordResponse>,
                response: Response<ActivityRecordResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                    Log.d("DoItRepository", "onResponseSuccess")
                } else{
                    Log.d("DoItRepository", "onResponseFail: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ActivityRecordResponse>, t: Throwable) {
                Log.d("DoItRepository", "onFailure: ${t.message}")
                callback(null)
            }
        })
    }

    // 일별 기록 조회
    fun getDailyRecord(
        date: String,
        callback: (ActivityDayRecordResponse?) -> Unit
    ) {
        doItService.getActivityDailyRecord(date).enqueue(object : Callback<ActivityDayRecordResponse> {
            override fun onResponse(
                call: Call<ActivityDayRecordResponse>,
                response: Response<ActivityDayRecordResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                    Log.d("DoItRepository", "onResponseSuccess")
                } else{
                    Log.d("DoItRepository", "onResponseFail: ${response.code()}")
                    callback(null)
                }
            }

            override fun onFailure(p0: Call<ActivityDayRecordResponse>, t: Throwable) {
                Log.d("DoItRepository", "onFailure: ${t.message}")
                callback(null)
            }
        })
    }

    // 일기 저장
    fun writeDiary(request: WriteDiaryReq, onSuccess: (DiaryResponse?) -> Unit) {
        doItService.writeDiary(request).enqueue(object : Callback<DiaryResponse> {
            override fun onResponse(call: Call<DiaryResponse>, response: Response<DiaryResponse>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                    Log.d("DoItRepository", "writeDiary Success")
                } else {
                    Log.e("DoItRepository", "writeDiary Failed: ${response.code()}")
                    onSuccess(null)
                }
            }

            override fun onFailure(call: Call<DiaryResponse>, t: Throwable) {
                Log.e("DoItRepository", "writeDiary Error: ${t.message}")
                onSuccess(null)
            }
        })
    }

    // 일기 조회
    fun getDiary(date: String, onSuccess: (DiaryResponse?) -> Unit) {
        doItService.getDiary(date).enqueue(object : Callback<DiaryResponse> {
            override fun onResponse(call: Call<DiaryResponse>, response: Response<DiaryResponse>) {
                if (response.isSuccessful) {
                    onSuccess(response.body())
                    Log.d("DoItRepository", "getDiary Success")
                } else {
                    Log.e("DoItRepository", "getDiary Failed: ${response.code()}")
                    onSuccess(null)
                }
            }

            override fun onFailure(call: Call<DiaryResponse>, t: Throwable) {
                Log.e("DoItRepository", "getDiary Error: ${t.message}")
                onSuccess(null)
            }
        })
    }

    // 챗봇 기록 메뉴 조회
    fun getChatbotHistoryMenu(request: UserDailyBotChatChoiceRequest, onSuccess: (UserDailyBotChatChoiceResponse?) -> Unit){
        doItService.getChatbotHistoryMenu(request).enqueue(object : Callback<UserDailyBotChatChoiceResponse>{
            override fun onResponse(
                call: Call<UserDailyBotChatChoiceResponse>,
                response: Response<UserDailyBotChatChoiceResponse>
            ) {
                if(response.isSuccessful){
                    onSuccess(response.body())
                    Log.d("DoItRepository", "getChatbotHistoryMenu Success")
                }else{
                    Log.d("DoItRepository", "getChatbotHistoryMenu Failed: ${response.code()}")
                    onSuccess(null)
                }
            }

            override fun onFailure(call: Call<UserDailyBotChatChoiceResponse>, t: Throwable) {
                Log.d("DoItRepository", "getChatbotHistoryMenu Error: ${t.message}")
            }

        })
    }

    // 챗봇 상담 기록 조회
    fun getChatbotHistory(request: UserDailyBotChatRequest, onSuccess: (UserDailyBotChatResponse?) -> Unit){
        doItService.getChatbotHistory(request).enqueue(object : Callback<UserDailyBotChatResponse>{
            override fun onResponse(
                call: Call<UserDailyBotChatResponse>,
                response: Response<UserDailyBotChatResponse>
            ) {
                if(response.isSuccessful){
                    onSuccess(response.body())
                    Log.d("DoItRepository", "getChatbotHistory Success")
                }else{
                    Log.d("DoItRepository", "getChatbotHistory Failed: ${response.code()}")
                    onSuccess(null)
                }
            }

            override fun onFailure(call: Call<UserDailyBotChatResponse>, t: Throwable) {
                Log.d("DoItRepository", "getChatbotHistory Error: ${t.message}")
            }
        })
    }

    // 몽 대화 기록 조회
    fun getMongChatHistory(request: UserDailyChatRequest, onSuccess: (UserDailyChatResponse?) -> Unit){
        doItService.getMongChatHistory(request).enqueue(object : Callback<UserDailyChatResponse>{
            override fun onResponse(
                call: Call<UserDailyChatResponse>,
                response: Response<UserDailyChatResponse>
            ) {
                if(response.isSuccessful){
                    onSuccess(response.body())
                    Log.d("DoItRepository", "getMongChatHistory Success")
                }else{
                    Log.d("DoItRepository", "getMongChatHistory Failed: ${response.code()}")
                    onSuccess(null)
                }
            }

            override fun onFailure(call: Call<UserDailyChatResponse>, t: Throwable) {
                Log.d("DoItRepository", "getMongChatHistory Error: ${t.message}")
            }
        })
    }
}
