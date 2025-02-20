package com.example.durymong.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object TokenManager {
    private const val PREF_NAME = "durymong_prefs"
    private const val TOKEN_KEY = "auth_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"

    private lateinit var prefs: SharedPreferences
    private val _accessTokenLiveData = MutableLiveData<String?>()
    val accessTokenLiveData: LiveData<String?> get() = _accessTokenLiveData
    private val _refreshTokenLiveData = MutableLiveData<String?>()
    val refreshTokenLiveData: LiveData<String?> get() = _refreshTokenLiveData

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        _accessTokenLiveData.value = prefs.getString(TOKEN_KEY, null)
        Log.d("TokenManager", "init: _accessTokenLiveData.value = ${_accessTokenLiveData.value}")
    }

    fun saveToken(token: String) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
        _accessTokenLiveData.postValue(token)  // LiveData 업데이트
    }

    fun saveRefreshToken(refreshToken: String) {
        prefs.edit().putString(REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    fun getAccessToken(): String? = prefs.getString(TOKEN_KEY, null)

    fun getRefreshToken(): String? = prefs.getString(REFRESH_TOKEN_KEY, null)

    fun clearToken() {
        prefs.edit().remove(TOKEN_KEY).apply()
        prefs.edit().remove(REFRESH_TOKEN_KEY).apply()
        _accessTokenLiveData.postValue(null)
        _refreshTokenLiveData.postValue(null)
    }
}