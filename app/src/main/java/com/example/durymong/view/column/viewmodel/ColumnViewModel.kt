package com.example.durymong.view.column.viewmodel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.durymong.R
import com.example.durymong.model.dto.response.column.Category
import com.example.durymong.model.dto.response.column.ColumnResult
import com.example.durymong.model.repository.ColumnRepository
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize


class ColumnViewModel : ViewModel() {
    private val repository = ColumnRepository()

    //실제 data를 저장할 변수들
    private val _columnCategoryList = MutableLiveData<List<Category>>(emptyList())
    private val _columnData = MutableLiveData<ColumnResult>()

    //화면에서 아래 변수들을 통해 값에 접근
    val columnCategoryList: LiveData<List<Category>> get() = _columnCategoryList
    val columnData: LiveData<ColumnResult> get() = _columnData

    init {
        //처음 viewModel이 생성될 때 실행할 작업들
        Log.d("ColumnViewModel", "init")
        fetchColumnCategoryData()
    }

    fun fetchColumnCategoryData() {
        viewModelScope.launch {
            repository.getColumnCategories { response ->
                Log.d("ColumnViewModel", "in viewModelScope")
                if (response != null) {
                    Log.d("ColumnViewModel", "카테고리 가져오기 성공")
                    _columnCategoryList.value = response.result.categories
                } else {
                    Log.e("ColumnViewModel", "카테고리 가져오기 실패")
                }
            }
        }
        Log.d("ColumnViewModel", "fetchColumnCategoryData Done")
    }

    fun searchColumnByKeyword(keyword: String) {
        viewModelScope.launch {
            repository.searchColumns(keyword) { response ->
                if (response != null) {
                    Log.d("ColumnViewModel", "키워드 검색 성공")
                    // TODO: 데이터 처리
                } else {
                    Log.e("ColumnViewModel", "키워드 검색 실패")
                }
            }
        }
    }

    fun fetchColumnData(categoryId: Int) {
        viewModelScope.launch {
            repository.getColumns(categoryId) { response ->
                if (response != null) {
                    Log.d("ColumnViewModel", "칼럼 조회 성공")
                    // 데이터 처리
                    _columnData.value = response.result
                } else {
                    Log.e("ColumnViewModel", "칼럼 조회 실패")
                }
            }
        }
    }
}