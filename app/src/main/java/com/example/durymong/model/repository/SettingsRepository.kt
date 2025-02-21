package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.request.settings.PasswordRequestDto
import com.example.durymong.model.dto.request.settings.UserInfoRequestDto
import com.example.durymong.model.dto.response.settings.DeleteHistoryResponse
import com.example.durymong.model.dto.response.settings.PasswordResponseDto
import com.example.durymong.model.dto.response.settings.UserEliminationResponse
import com.example.durymong.model.dto.response.settings.UserEliminationResult
import com.example.durymong.model.dto.response.settings.UserInfoResponseDto
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.SettingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsRepository {

    private val service: SettingService = RetrofitObject.createService()

    fun editPassword(nowPassword: String, newPassword: String, callback: (Boolean, String) -> Unit) {
        val request = PasswordRequestDto(nowPassword, newPassword)
        service.postPasswordModification(request).enqueue(object : Callback<PasswordResponseDto> {
            override fun onResponse(
                call: Call<PasswordResponseDto>,
                response: Response<PasswordResponseDto>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    Log.d("successful", "비밀번호 수정 성공 : ${result.message} ")
                    callback(true, result.message) //callback 호출 -> viewmodel에서 사용할 수 있도록
                } else {
                    Log.d("fail", "비밀번호 수정 실패: ${response.code()}")
                    callback(false, "비밀번호 변경 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PasswordResponseDto>, t: Throwable) {
                Log.d("fail", "네트워크 요청 실패: ${t.message}")
                callback(false, "네트워크 오류 발생")
            }

        })
    }

    fun editUserInfo(newUserId: String, newMongName: String, callback: (Boolean, String) -> Unit){
        val request = UserInfoRequestDto(newUserId, newMongName)
        service.postInfoModification(request).enqueue(object : Callback<UserInfoResponseDto> {
            override fun onResponse(
                call: Call<UserInfoResponseDto>,
                response: Response<UserInfoResponseDto>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    Log.d("successful", "유저 정보 수정 성공 : ${result.message} ")
                    callback(true, result.message) //callback 호출 -> viewmodel에서 사용할 수 있도록
                } else {
                    Log.d("fail", "유저 정보 수정 실패: ${response.code()}")
                    callback(false, "유저 정보 변경 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserInfoResponseDto>, t: Throwable) {
                Log.d("fail", "네트워크 요청 실패: ${t.message}")
                callback(false, "네트워크 오류 발생")
            }

        })
    }

    fun deleteUserHistory(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        service.deleteHistory().enqueue(object : Callback<DeleteHistoryResponse> {
            override fun onResponse(
                call: Call<DeleteHistoryResponse>,
                response: Response<DeleteHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success) {
                            onSuccess(it.message)
                        } else {
                            onFailure(it.message)
                        }
                    } ?: onFailure("응답이 없습니다.")
                } else {
                    onFailure("서버 오류 발생")
                }
            }

            override fun onFailure(call: Call<DeleteHistoryResponse>, t: Throwable) {
                onFailure("네트워크 오류 발생")
            }
        })
    }

    fun getUserEliminationInfo(
        onSuccess: (UserEliminationResult) -> Unit,
        onFailure: (String) -> Unit
    ) {
        service.getUserEliminationInfo().enqueue(object : Callback<UserEliminationResponse> {
            override fun onResponse(
                call: Call<UserEliminationResponse>,
                response: Response<UserEliminationResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success) {
                            it.result?.let(onSuccess)
                        } else {
                            onFailure(it.message)
                        }
                    } ?: onFailure("응답이 없습니다.")
                } else {
                    onFailure("서버 오류 발생")
                }
            }

            override fun onFailure(call: Call<UserEliminationResponse>, t: Throwable) {
                onFailure("네트워크 오류 발생")
            }
        })
    }
}
