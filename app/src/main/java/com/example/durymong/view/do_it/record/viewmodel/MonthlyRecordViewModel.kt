package com.example.durymong.view.do_it.record.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.durymong.model.dto.response.doit.DateInfo
import com.example.durymong.model.repository.DoItRepository
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MonthlyRecordViewModel : ViewModel() {
    private val repository = DoItRepository()

    private val _monthlyRecordList = MutableLiveData<List<DateInfo>>()
    val monthlyRecordList: LiveData<List<DateInfo>> get() = _monthlyRecordList

    private val _currentMonth = MutableLiveData<YearMonth>()
    val currentMonth: LiveData<YearMonth> get() = _currentMonth

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _mongName = MutableLiveData<String>()
    val mongName: LiveData<String> get() = _mongName

    private val _mongImg = MutableLiveData<String>()
    val mongImg: LiveData<String> get() = _mongImg

    init {
        val currentDate = YearMonth.now()
        _currentMonth.value = currentDate
        fetchMonthlyRecord(currentDate.year, currentDate.monthValue)
    }

    fun updateSelectedDate(dateInfo: DateInfo) {
        _selectedDate.value = dateInfo.date
        Log.d("MonthlyRecordViewModel", "Selected Date: ${dateInfo.date}")
    }

    fun changeMonth(offset: Int) {
        _currentMonth.value = _currentMonth.value?.plusMonths(offset.toLong())
        _currentMonth.value?.let {
            fetchMonthlyRecord(it.year, it.monthValue)
        }
    }

    fun updateMonthlyRecord(
        dayCountList: List<DateInfo>,
        year: Int,
        month: Int
    ): List<DateInfo> {
        val yearMonth = YearMonth.of(year, month)
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.lengthOfMonth()
        val startDayOfWeek = firstDayOfMonth.dayOfWeek.value

        val recordList = mutableListOf<DateInfo>()

        // 빈 칸 추가 (시작 요일 맞추기)
        for (i in 0 until startDayOfWeek) {
            recordList.add(DateInfo("", 0))
        }

        // 날짜에 해당하는 활동 개수 매핑
        val dateActivityMap = dayCountList.associateBy { it.date }
        for (day in 1..lastDayOfMonth) {
            val dateString = yearMonth.atDay(day).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val activityCount = dateActivityMap[dateString]?.count ?: 0
            recordList.add(DateInfo(dateString, activityCount))
        }

        return recordList
    }

    fun fetchMonthlyRecord(year: Int, month: Int) {
        repository.getMonthlyRecord(year, month) { response ->
            if (response != null) {
                _nickname.value = response.result.nickname
                val newMonthlyData = updateMonthlyRecord(response.result.dayCountList, year, month)
                _monthlyRecordList.value = newMonthlyData
            }
        }
    }
}
