package com.example.durymong.model

import android.util.Log
import com.example.durymong.model.dto.request.user.ReIssueTokenRequest
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.UserService
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = TokenManager.getAccessToken()

//        return if (!token.isNullOrBlank()) {
//            val newRequest = originalRequest.newBuilder()
//                .addHeader("Authorization", "Bearer $token")
//                .build()
//            chain.proceed(newRequest)
//        } else {
//            chain.proceed(originalRequest)
//        }

        val requestWithToken = token?.let {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        } ?: originalRequest

        val response = chain.proceed(requestWithToken)
        Log.d("AuthInterceptor", "response code: ${response.code}")

        return if (response.code == 401) {
            response.close() // 기존 응답을 닫아줌
            synchronized(this) {
                val newToken = refreshToken() // 토큰 갱신
                if (!newToken.isNullOrBlank()) {
                    val newRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                    return chain.proceed(newRequest)
                }
            }
            response
        } else {
            response
        }
    }

    private fun refreshToken(): String?{
        val refreshToken = TokenManager.getRefreshToken() ?: return null

        val userService: UserService = RetrofitObject.createService()
        val call = userService.postNewTokens(ReIssueTokenRequest(refreshToken))

        return try {
            val response = call.execute() // 동기 호출
            if (response.isSuccessful) {
                response.body()?.let {
                    TokenManager.saveToken(it.result.newAccessToken) // 새 access token 저장
                    TokenManager.saveRefreshToken(it.result.newRefreshToken)
                    return it.result.newAccessToken
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}