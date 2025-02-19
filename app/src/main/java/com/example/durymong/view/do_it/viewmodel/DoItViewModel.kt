package com.example.durymong.view.do_it.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.model.dto.request.doit.CheckActivityRequest
import com.example.durymong.model.dto.request.doit.UserDailyBotChatChoiceRequest
import com.example.durymong.model.dto.request.doit.UserDailyBotChatRequest
import com.example.durymong.model.dto.request.doit.UserDailyChatRequest
import com.example.durymong.model.dto.request.doit.WriteDiaryReq
import com.example.durymong.model.dto.response.doit.ActivityTestListResponse
import com.example.durymong.model.dto.response.doit.BotChatDto
import com.example.durymong.model.dto.response.doit.ChatBotHistory
import com.example.durymong.model.dto.response.doit.DateInfo
import com.example.durymong.model.dto.response.doit.UserDailyBotChatResult
import com.example.durymong.model.dto.response.doit.UserDailyMongChatResult
import com.example.durymong.model.repository.DoItRepository
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class DoItViewModel : ViewModel() {
    private val repository = DoItRepository()

    private var _doItMainPage = MutableLiveData<ActivityTestListResponse>()

    val doItMainPage: MutableLiveData<ActivityTestListResponse> get() = _doItMainPage

    // 월별 기록 조회, 일별 기록 조회에 사용
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

    //일기 조회
    private val _diary = MutableLiveData<String>()
    val diary: LiveData<String> get() = _diary

    // 챗봇 대화 목록
    private val _chatBotCardList = MutableLiveData<List<BotChatDto>>()
    val chatBotCardList: LiveData<List<BotChatDto>> get() = _chatBotCardList

    // 선택된 챗봇 대화 기록
    private val _selectedChatBotHistory = MutableLiveData<UserDailyBotChatResult>()
    val selectedChatBotHistory: LiveData<UserDailyBotChatResult> get() = _selectedChatBotHistory

    // 선택된 몽 대화 기록
    private val _selectedMongChatHistory = MutableLiveData<UserDailyMongChatResult>()
    val selectedMongChatHistory: LiveData<UserDailyMongChatResult> get() = _selectedMongChatHistory

    init {
        loadTestMainPage()
    }

    fun submitCheck(checkActivityRequest: CheckActivityRequest) {
        viewModelScope.launch {
            try {
                DoItRepository().submitCheck(checkActivityRequest)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cancelCheck(id: Int) {
        DoItRepository().cancelCheck(id)
    }

    fun loadTestMainPage() {
        viewModelScope.launch {
            try {
                DoItRepository().getDoItMainPage(
                    onSuccess = { dto ->
                        // 여기서 받은 dto를 LiveData에 담아 UI로 전달
                        _doItMainPage.value = dto
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 월별 기록 조회
    fun changeMonth(offset: Int) {
        val newMonth = _currentMonth.value?.plusMonths(offset.toLong()) ?: return

        fetchMonthlyRecord(newMonth.year, newMonth.monthValue) { success ->
            if (success) {
                _currentMonth.value = newMonth
                Log.d("DoItViewModel", "Current Month: ${newMonth.monthValue}")
            } else {
                Log.d("DoItViewModel", "Failed to fetch monthly record")
            }
        }
    }

    fun updateCurrentDate() {
        val currentDate = YearMonth.now()
        _currentMonth.value = currentDate
        fetchMonthlyRecord(currentDate.year, currentDate.monthValue) {
            if (it) {
                Log.d("DoItViewModel", "Current Month: ${currentDate.monthValue}")
            }
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

    fun fetchMonthlyRecord(year: Int, month: Int, onComplete: (Boolean) -> Unit) {
        repository.getMonthlyRecord(year, month) { response ->
            if (response != null) {
                _nickname.value = response.result.nickname
                val newMonthlyData = updateMonthlyRecord(response.result.dayCountList, year, month)
                _monthlyRecordList.value = newMonthlyData
                onComplete(true)
            } else {
                onComplete(false)
            }
        }
    }

    // 일별 기록 조회
    fun updateSelectedDate(dateInfo: DateInfo) {
        _selectedDate.value = dateInfo.date
        fetchDailyRecord(dateInfo.date)
        Log.d("MonthlyRecordViewModel", "Selected Date: ${dateInfo.date}")
    }

    fun fetchDailyRecord(date: String) {
        repository.getDailyRecord(date) { response ->
            if (response != null) {
                _mongName.value = response.result.mongName
                _mongImg.value = response.result.mongImage
            }
        }
    }

    // 일기 저장
    fun writeDiary(request: WriteDiaryReq) {
        viewModelScope.launch {
            repository.writeDiary(request) { response ->
                if (response != null) {
                    Log.d("DoItViewModel", "Diary 작성 성공")
                } else {
                    Log.e("DoItViewModel", "Diary 작성 실패")
                }
            }
        }
    }

    // 일기 조회
    fun fetchDiary(date: String) {
        viewModelScope.launch {
            repository.getDiary(date) { response ->
                if (response != null) {
                    Log.d("DoItViewModel", "Diary 조회 성공")
                    _diary.value = response.result.content
                    _mongImg.value = response.result.mongImage
                } else {
                    Log.e("DoItViewModel", "Diary 조회 실패")
                }
            }
        }
    }

    // 챗봇 대화 목록 조회
    fun fetchChatBotMenu(date: String){
        viewModelScope.launch {
            repository.getChatbotHistoryMenu(UserDailyBotChatChoiceRequest(date)){ response ->
                if (response != null){
                    Log.d("DoItViewModel", "챗봇 메뉴 조회 성공")
                    _chatBotCardList.value = response.result.botChatDtos
                } else{
                    Log.e("DoItViewModel", "챗봇 메뉴 조회 실패")
                }
            }
        }
    }

    // 챗봇 대화 기록 조회
    fun fetchDailyChatBotHistory(date: String, chatBotId: Int){
        viewModelScope.launch {
            repository.getChatbotHistory(UserDailyBotChatRequest(date, chatBotId)){ response ->
                if (response != null){
                    Log.d("DoItViewModel", "챗봇 기록 조회 성공")
                    _selectedChatBotHistory.value = response.result
                } else{
                    Log.e("DoItViewModel", "챗봇 기록 조회 실패")
                }
            }
        }
    }

    fun fetchDailyMongChatHistory(date: String){
        viewModelScope.launch {
            repository.getMongChatHistory(UserDailyChatRequest(date)){ response ->
                if (response != null){
                    Log.d("DoItViewModel", "몽 대화 기록 조회 성공")
                    _selectedMongChatHistory.value = response.result
                } else{
                    Log.e("DoItViewModel", "몽 대화 기록 조회 실패")
                }
            }
        }
    }

}