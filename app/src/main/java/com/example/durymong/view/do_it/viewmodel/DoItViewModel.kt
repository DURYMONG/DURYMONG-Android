package com.example.durymong.view.do_it.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.model.dto.request.doit.CheckActivityRequest
import com.example.durymong.model.dto.response.doit.ActivityTestListResponse
import com.example.durymong.model.repository.DoItRepository
import kotlinx.coroutines.launch

class DoItViewModel : ViewModel() {
    private var _doItMainPage = MutableLiveData<ActivityTestListResponse>()

    val doItMainPage: MutableLiveData<ActivityTestListResponse> get() = _doItMainPage

    init{
        loadTestMainPage()
    }

    fun submitCheck(checkActivityRequest: CheckActivityRequest) {
        viewModelScope.launch{
            try{
                DoItRepository().submitCheck(checkActivityRequest)
            }catch (e: Exception){
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
                DoItRepository().getDoItMainPage (
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
}