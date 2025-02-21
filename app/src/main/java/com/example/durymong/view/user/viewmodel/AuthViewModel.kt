package com.example.durymong.view.user.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.model.TokenManager
import com.example.durymong.model.dto.request.user.UserLoginRequestDto
import com.example.durymong.model.dto.response.user.ApiResponse
import com.example.durymong.model.dto.response.user.IdCheckResponse
import com.example.durymong.model.dto.response.user.PasswordCheckResponse
import com.example.durymong.model.dto.response.user.RegisterResponse
import com.example.durymong.model.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _checkUserIdResult = MutableLiveData<IdCheckResponse<String>>()
    val checkUserIdResult: LiveData<IdCheckResponse<String>> get() = _checkUserIdResult

    private val _emailCheckResult = MutableLiveData<ApiResponse<String>>()
    val emailCheckResult: LiveData<ApiResponse<String>> get() = _emailCheckResult

    private val _emailAuthRequestResult = MutableLiveData<ApiResponse<String>>()
    val emailAuthRequestResult: LiveData<ApiResponse<String>> get() = _emailAuthRequestResult

    private val _emailAuthVerificationResult = MutableLiveData<ApiResponse<String>>()
    val emailAuthVerificationResult: LiveData<ApiResponse<String>> get() = _emailAuthVerificationResult

    private val _passwordCheckResult = MutableLiveData<PasswordCheckResponse>()
    val passwordCheckResult: LiveData<PasswordCheckResponse> get() = _passwordCheckResult

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> get() = _registerResult

    fun loginTest() {
        val userData = UserLoginRequestDto(
            id = "durymong",
            password = "durymong12"
        )

        viewModelScope.launch {
            repository.postLogin(userData) { response ->
                if (response != null) {
                    Log.d("AuthViewModel", "로그인 성공")
                    val token = response.result.accessToken
                    val refreshToken = response.result.refreshToken
                    if (token.isNotBlank()) {
                        TokenManager.saveToken(token)
                        Log.d("AuthViewModel", "토큰 저장 성공 : $token")
                        TokenManager.saveRefreshToken(refreshToken)
                        Log.d("AuthViewModel", "refresh 토큰 저장 성공 : $refreshToken")
                    } else{
                        Log.e("AuthViewModel", "토큰 저장 실패")
                    }
                } else {
                    Log.e("AuthViewModel", "로그인 실패")
                }
            }
        }
    }

    fun checkUserId(userId: String) {
        viewModelScope.launch {
            val result = repository.checkUserId(userId)
            _checkUserIdResult.postValue(result)
        }
    }

    fun checkEmailDuplicate(email: String) {
        viewModelScope.launch {
            _emailCheckResult.value = repository.checkEmailDuplicate(email)
        }
    }

    fun requestEmailAuthCode(email: String) {
        viewModelScope.launch {
            _emailAuthRequestResult.value = repository.requestEmailAuthCode(email)
        }
    }

    fun verifyEmailAuthCode(email: String, authCode: String) {
        viewModelScope.launch {
            _emailAuthVerificationResult.value = repository.verifyEmailAuthCode(email, authCode)
        }
    }

    fun checkPasswordValidity(password: String) {
        viewModelScope.launch {
            val response = repository.checkPassword(password)
            _passwordCheckResult.value = response
        }
    }

    fun registerUser(userId: String, email: String, password: String) {
        viewModelScope.launch {
            Log.d("회원가입 API 호출", "회원가입 요청 시작: userId=$userId, email=$email, password=$password")

            val response = repository.registerUser(userId, email, password)

            Log.d("회원가입 API 응답", "성공 여부: ${response.success}, 코드: ${response.code}, 메시지: ${response.message}")

            _registerResult.postValue(response)
        }
    }

}