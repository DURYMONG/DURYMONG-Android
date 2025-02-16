package com.example.durymong.view.do_it.test_page.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.model.dto.request.doit.SubmitTestRequestDto
import com.example.durymong.model.dto.response.doit.QuestionDto
import com.example.durymong.model.dto.response.doit.SubmitTestResponseDto
import com.example.durymong.model.dto.response.doit.TestPageResponseDto
import com.example.durymong.model.repository.DoItRepository
import kotlinx.coroutines.launch

class TestPageViewModel : ViewModel() {

    private var _testPageResponseData = MutableLiveData<TestPageResponseDto>()
    private var _testResult = MutableLiveData<SubmitTestResponseDto>()
    private var _testPageList = MutableLiveData<List<TestPageData>>()


    val testPageList: LiveData<List<TestPageData>> get() = _testPageList
    val testResult: MutableLiveData<SubmitTestResponseDto> get() = _testResult

    // 각 테스트 항목

    fun loadTestPageData(testId: Int) {
        viewModelScope.launch {
            try {
                DoItRepository().getTestData(testId,
                    onSuccess = { dto ->
                        _testPageResponseData.value = dto
                        Log.d("loadTestPageData", dto.result.questionList[0].question)
                        val testPageData = mutableListOf<TestPageData>()
                        val questionList = dto.result.questionList
                        for (question in questionList) {
                            testPageData.add(
                                TestPageData(
                                    question,
                                    0,
                                    dto.result.numberOfOptions,
                                     false
                                )
                            )
                        }
                        _testPageList.value = testPageData
                        Log.d("loadTestPageData", testPageList.value!![0].questionId.question)

                    })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun loadTestResult(testId: Int, submitTestRequestDto: SubmitTestRequestDto) {
        viewModelScope.launch {
            try {
                DoItRepository().getTestResult(
                    testId = testId,
                    submitTestRequestDto = submitTestRequestDto,
                    onSuccess = { dto ->
                        // 여기서 받은 dto를 LiveData에 담아 UI로 전달
                        _testResult.value = dto
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
