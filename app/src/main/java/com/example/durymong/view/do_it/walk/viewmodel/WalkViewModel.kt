package com.example.durymong.view.do_it.walk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WalkViewModel : ViewModel() {

    // 실제 저장 데이터
    private val _timeData = MutableLiveData<Pair<Int,Int>>()
    private val _isDialogOpen = MutableLiveData<Boolean>()

    // 값 접근
    val timeData: LiveData<Pair<Int,Int>> get() = _timeData
    val isDialogOpen: LiveData<Boolean> get() = _isDialogOpen

    fun openDialog() {
        _isDialogOpen.value = true
    }

    fun closeDialog() {
        _isDialogOpen.value = false
        Log.d("closeDialog", "closeDialog")
    }

    init {
        _timeData.value = Pair(0, 0) // 0시간 0분
    }

    fun sendData(hour: Int, minute: Int) {
        _timeData.value = Pair(hour, minute)
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            var hours = 0
            var minutes = 0
            while (true) {
                delay(1000) // 1분마다 업데이트
                minutes++
                if (minutes == 60) {
                    minutes = 0
                    hours++
                }
                _timeData.postValue(Pair(hours, minutes))
            }
        }
    }



}