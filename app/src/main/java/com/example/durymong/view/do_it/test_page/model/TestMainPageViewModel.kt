package com.example.durymong.view.do_it.test_page.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.model.dto.response.doit.TestMainPageResponseDto
import com.example.durymong.model.repository.DoItRepository
import kotlinx.coroutines.launch

class TestMainPageViewModel: ViewModel() {

    private var _testMainPage = MutableLiveData<TestMainPageResponseDto>()

    val testMainPageList: MutableLiveData<TestMainPageResponseDto> = _testMainPage

    init{
        loadTestMainPage(1)
    }

    private fun loadTestMainPage(testId: Int) {
        viewModelScope.launch {
            try {
                DoItRepository().getTestMainPage(
                    testId = testId,
                    onSuccess = { dto ->
                        // 여기서 받은 dto를 LiveData에 담아 UI로 전달
                        _testMainPage.value = dto
                        Log.d("TestMainPageViewModel", "loadTestMainPage")
                    },
                    onError = { throwable ->
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}
