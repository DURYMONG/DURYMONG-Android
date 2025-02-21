package com.example.durymong.model.repository

import android.util.Log
import com.example.durymong.model.dto.request.user.RegisterRequest
import com.example.durymong.model.dto.request.user.UserLoginRequestDto
import com.example.durymong.model.dto.response.user.ApiResponse
import com.example.durymong.model.dto.response.user.IdCheckResponse
import com.example.durymong.model.dto.response.user.PasswordCheckResponse
import com.example.durymong.model.dto.response.user.RegisterResponse
import com.example.durymong.model.dto.response.user.Token
import com.example.durymong.model.dto.response.user.UserTokenRequestDto
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserRepository {
    // 로그인, 회원가입과 같이 user 관련된 기능 수행하는 Repository
    // 여기에서 함수를 구현해서 call.enque 와 같은 작업 수행
    private val service: UserService = RetrofitObject.createService()

    fun postLogin(userData: UserLoginRequestDto, callback: (UserTokenRequestDto?) -> Unit) {
        service.postLogin(userData).enqueue(object : Callback<UserTokenRequestDto> {
            override fun onResponse(
                call: Call<UserTokenRequestDto>,
                response: Response<UserTokenRequestDto>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "알 수 없는 오류"
                    callback(UserTokenRequestDto(false, response.code(), errorMessage, Token("", "")))
                }
            }

            override fun onFailure(call: Call<UserTokenRequestDto>, t: Throwable) {
                callback(UserTokenRequestDto(false, -1, "네트워크 오류: ${t.message}", Token("", "")))
            }
        })
    }

    suspend fun checkUserId(userId: String): IdCheckResponse<String> {
        return withContext(Dispatchers.IO) {
            try {
                service.checkUserId(userId)
            } catch (e: HttpException) {
                IdCheckResponse(false, e.code(), "이미 존재하는 아이디입니다.", "")
            }
        }
    }

    suspend fun checkEmailDuplicate(email: String): ApiResponse<String> {
        return try {
            service.checkEmailDuplicate(email)
        } catch (e: Exception) {
            ApiResponse(false, -1, "이메일 중복 검사 실패", "")
        }
    }

    suspend fun requestEmailAuthCode(email: String): ApiResponse<String> {
        return try {
            service.requestEmailAuthCode(email)
        } catch (e: Exception) {
            ApiResponse(false, -1, "이메일 인증번호 요청 실패", "")
        }
    }

    suspend fun verifyEmailAuthCode(email: String, authCode: String): ApiResponse<String> {
        return try {
            service.verifyEmailAuthCode(email, authCode)
        } catch (e: Exception) {
            ApiResponse(false, -1, "이메일 인증 실패", "")
        }
    }

    suspend fun checkPassword(password: String): PasswordCheckResponse {
        return try {
            val response = service.checkPassword(password)
            if (response.isSuccessful) {
                response.body() ?: PasswordCheckResponse(false, 500, "서버 응답 없음", "")
            } else {
                PasswordCheckResponse(false, response.code(), "비밀번호가 유효하지 않습니다.", "")
            }
        } catch (e: Exception) {
            PasswordCheckResponse(false, 500, "서버 요청 실패: ${e.message}", "")
        }
    }

    suspend fun registerUser(userId: String, email: String, password: String): RegisterResponse {
        return try {
            Log.d("회원가입 API 요청", "회원가입 요청 시작: userId=$userId, email=$email, password=$password")

            val response = service.registerUser(RegisterRequest(userId, email, password))

            if (response.isSuccessful) {
                Log.d("회원가입 API 응답", "회원가입 성공")
                response.body() ?: RegisterResponse(false, 500, "서버 응답 없음", "")
            } else {
                Log.d("회원가입 API 응답 실패", "HTTP 상태 코드: ${response.code()}, 오류 메시지: ${response.errorBody()?.string()}")
                RegisterResponse(false, response.code(), "회원가입 실패", "")
            }
        } catch (e: Exception) {
            Log.e("회원가입 API 요청 오류", "서버 요청 실패: ${e.message}")
            RegisterResponse(false, 500, "서버 요청 실패: ${e.message}", "")
        }
    }

}