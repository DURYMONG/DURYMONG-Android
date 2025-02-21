package com.example.durymong.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {
    private const val PREF_NAME = "DurymongPrefs"
    private const val KEY_MONG_SELECTED = "mongSelected"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    // 몽 선택 여부 저장
    fun setMongSelected(isSelected: Boolean) {
        prefs.edit().putBoolean(KEY_MONG_SELECTED, isSelected).apply()
    }

    // 몽 선택 여부 확인
    fun isMongSelected(): Boolean {
        return prefs.getBoolean(KEY_MONG_SELECTED, false)
    }
}