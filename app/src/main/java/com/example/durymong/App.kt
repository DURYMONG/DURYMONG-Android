package com.example.durymong

import android.app.Application
import com.example.durymong.model.TokenManager

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        TokenManager.init(this)
    }
}