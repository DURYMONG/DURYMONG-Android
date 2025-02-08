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
    private val _timeData = MutableLiveData<Pair<Long,Long>>()

    // 값 접근
    val timeData: LiveData<Pair<Long,Long>> get() = _timeData

    init {
        _timeData.value = Pair(0, 0) // 0시간 0분
    }

    fun sendData(hour: Long, minute: Long) {
        var hourData= hour
        var minuteData = minute
        if(minuteData>59){
            hourData+=1
            minuteData-=60
        }
        if(hourData>24){
            hourData=24
        }
        _timeData.value = Pair(hourData, minuteData)
    }

}