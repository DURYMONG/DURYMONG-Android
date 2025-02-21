package com.example.durymong.util

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import kotlin.math.roundToInt

//Parcelable 객체 전달을 위한 Bundle 확장 함수
inline fun <reified T : Parcelable> Bundle.getSafeParcelable(key: String): T? =
    when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

//DP를 PX로 변환
fun Int.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).roundToInt()
}