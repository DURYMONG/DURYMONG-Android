package com.example.durymong.model

import android.util.Log
import com.example.durymong.model.dto.request.user.ReIssueTokenRequest
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.UserService
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean

class AuthInterceptor : Interceptor {
    private var isRefreshing = AtomicBoolean(false)
    private val lock = Object()

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

        // 토큰 갱신 로직에는 interceptor 적용 X
        if(originalRequest.url.encodedPath.contains("/auth/newtokens")){
            return chain.proceed(originalRequest)
        }

        val requestWithToken = token?.let {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $it")
                .build()
        } ?: originalRequest

        val response = chain.proceed(requestWithToken)
        Log.d("AuthInterceptor", "response code: ${response.code}")

        if (response.code == 403) {
//            val errorCode = getErrorCodeFromResponseBody(response.peekBody(Long.MAX_VALUE))
//            Log.d("AuthInterceptor", "서버 응답 error code: $errorCode") // 서버에서 반환한 "code" 값 출력
            response.close() // 기존 응답을 닫아줌
            synchronized(lock) {
                if(isRefreshing.get()){
                    Log.d("AuthInterceptor", "이미 토큰 갱신중, 기존 요청 대기")
                    while (isRefreshing.get()) {
                        Log.d("AuthInterceptor", "in lock")
                        try {
                            lock.wait() // 토큰 갱신이 끝날 때까지 대기
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    Log.d("AuthInterceptor", "토큰 갱신 완료됨, 새로운 요청 진행")
                    val newToken = TokenManager.getAccessToken()
                    return if (!newToken.isNullOrBlank()) {
                        val newRequest = originalRequest.newBuilder()
                            .addHeader("Authorization", "Bearer $newToken")
                            .build()
                        chain.proceed(newRequest)
                    } else {
                        Log.e("AuthInterceptor", "새 토큰 없음, 요청 진행 불가")
                        newErrorResponse(originalRequest,401,"토큰 갱신 실패")
                    }
                }
                isRefreshing.set(true)
            }
            val newToken = refreshToken() // 토큰 갱신

            synchronized(lock){
                isRefreshing.set(false)
                lock.notifyAll()
            }

            if (!newToken.isNullOrBlank()) {
                val newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $newToken")
                    .build()
                return chain.proceed(newRequest)
            } else{
                Log.e("AuthInterceptor", "토큰 갱신 실패, 로그아웃 처리")
                TokenManager.clearToken()
                return newErrorResponse(originalRequest,401,"토큰 갱신 실패")
            }
        }
        return response
    }

    private fun refreshToken(): String?{
        val refreshToken = TokenManager.getRefreshToken() ?: return null

        val userService: UserService = RetrofitObject.createRefreshService()
        val call = userService.postNewTokens(ReIssueTokenRequest(refreshToken))

        return try {
            val response = call.execute() // 동기 호출
            if (response.isSuccessful) {
                response.body()?.let {
                    TokenManager.saveToken(it.result.newAccessToken) // 새 access token 저장
                    Log.d("AuthInterceptor", "newAccessToken: ${it.result.newAccessToken}")
                    TokenManager.saveRefreshToken(it.result.newRefreshToken)
                    Log.d("AuthInterceptor","newRefreshToken: ${it.result.newRefreshToken}")
                    return it.result.newAccessToken
                }
            } else {
                Log.e("AuthInterceptor", "토큰 갱신 실패, 응답 코드: ${response.code()}")
                if (response.code() == 403){
                    Log.e("AuthInterceptor", "403 Forbidden: 로그아웃 처리")
                    TokenManager.clearToken()
                }
            }
            null
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("AuthInterceptor", "토큰 갱신 중 오류 발생: ${e.message}")
            null
        }
    }

    private fun newErrorResponse(request: Request, code: Int, message: String): Response {
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message(message)
            .body("{\"error\":\"$message\"}".toResponseBody(null))
            .build()
    }
}