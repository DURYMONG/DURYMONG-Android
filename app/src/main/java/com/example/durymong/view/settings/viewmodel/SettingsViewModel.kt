package com.example.durymong.view.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.durymong.model.dto.response.settings.UserEliminationResult
import com.example.durymong.model.repository.SettingsRepository

class SettingsViewModel : ViewModel() {
    private val repository = SettingsRepository()

    private val _changePasswordResult = MutableLiveData<Pair<Boolean, String>>()
    val changePasswordResult: LiveData<Pair<Boolean, String>> get() = _changePasswordResult

    private val _changeUserInfoResult = MutableLiveData<Pair<Boolean, String>>()
    val changeUserInfoResult: LiveData<Pair<Boolean, String>> get() = _changeUserInfoResult

    private val _deleteHistoryResult = MutableLiveData<Pair<Boolean, String>>()
    val deleteHistoryResult: LiveData<Pair<Boolean, String>> get() = _deleteHistoryResult

    private val _userEliminationInfo = MutableLiveData<UserEliminationResult>()
    val userEliminationInfo: LiveData<UserEliminationResult> get() = _userEliminationInfo

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun changePassword(nowPassword: String, newPassword: String) {
        repository.editPassword(nowPassword, newPassword) { success, message ->
            _changePasswordResult.postValue(Pair(success, message))
        }
    }

    fun changeUserInfo(newUserId : String, newMongName : String) {
        repository.editUserInfo(newUserId, newMongName) { success, message ->
            _changeUserInfoResult.postValue(Pair(success, message))
        }
    }

    fun deleteUserHistory() {
        repository.deleteUserHistory(
            onSuccess = { message ->
                _deleteHistoryResult.value = Pair(true, message)
            },
            onFailure = { errorMessage ->
                _deleteHistoryResult.value = Pair(false, errorMessage)
            }
        )
    }

    fun fetchUserEliminationInfo() {
        repository.getUserEliminationInfo(
            onSuccess = { result ->
                _userEliminationInfo.value = result
            },
            onFailure = { error ->
                _errorMessage.value = error
            }
        )
    }
}