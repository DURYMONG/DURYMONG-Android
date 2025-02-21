package com.example.durymong.view.chat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.durymong.model.dto.response.chat.ChatBot
import com.example.durymong.model.dto.response.chat.ChatStartResponse
import com.example.durymong.model.repository.ChatRepository

class ChatBotViewModel : ViewModel() {
    private val repository = ChatRepository()

    private val _chatBots = MutableLiveData<List<ChatBot>?>()
    val chatBots: LiveData<List<ChatBot>?> get() = _chatBots

    private val _chatStartResponse = MutableLiveData<ChatStartResponse?>()
    val chatStartResponse: MutableLiveData<ChatStartResponse?> get() = _chatStartResponse

    fun loadChatBots() {
        repository.getChatBots { response ->
            _chatBots.postValue(response)
        }
    }

    fun startChat(chatBotId: Int) {
        repository.startChat(chatBotId) { response ->
            _chatStartResponse.postValue(response)
        }
    }
}