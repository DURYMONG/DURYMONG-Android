package com.example.durymong.view.mong.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.model.dto.request.mong.MongCreateRequest
import com.example.durymong.model.dto.response.mong.ChatMessage
import com.example.durymong.model.dto.response.mong.HomeData
import com.example.durymong.model.dto.response.user.ApiResponse
import com.example.durymong.model.repository.ColumnRepository
import com.example.durymong.model.repository.MongRepository
import kotlinx.coroutines.launch

class HomeDataViewModel :ViewModel() {

    private val repository = MongRepository()

    private val _homeData = MutableLiveData<HomeData?>()
    val homeData: LiveData<HomeData?> get() = _homeData

    private val _chatHistory = MutableLiveData<List<ChatMessage>?>()
    val chatHistory: LiveData<List<ChatMessage>?> get() = _chatHistory

    private val _createMongResult = MutableLiveData<ApiResponse<String>>()
    val createMongResult: LiveData<ApiResponse<String>> get() = _createMongResult

    fun loadHomeData() {
        repository.getHomeData { response ->
            _homeData.postValue(response?.result)
        }
    }

    fun loadChatHistory() {
        repository.getChatHistory { response ->
            _chatHistory.postValue(response?.userChatHistory)
        }
    }

    fun createMong(mongName: String, mongType: String, mongColor: String) {
        viewModelScope.launch {
            val request = MongCreateRequest(mongName, mongType, mongColor)
            _createMongResult.value = repository.createMong(request)
        }
    }

}