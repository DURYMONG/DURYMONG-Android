package com.example.durymong.retrofit

import com.example.durymong.BuildConfig
import com.example.durymong.model.AuthInterceptor
import com.example.durymong.retrofit.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

object RetrofitObject {
    private const val BASE_URL = BuildConfig.BASE_URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = AuthInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    private val refreshClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit by lazy{
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 토큰 갱신용 retrofit
    private val refreshRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(refreshClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Repository에서 서비스를 생성할 때 사용
    // ex) private val service: SampleService = RetrofitObject.createService()
    inline fun <reified T> createService(): T = retrofit.create(T::class.java)

    // 토큰 Refresh 서비스
    fun createRefreshService(): UserService = refreshRetrofit.create(UserService::class.java)
}