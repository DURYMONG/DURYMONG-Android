package com.example.durymong.view.do_it.test_page.model

import com.example.durymong.model.dto.response.doit.QuestionDto

data class TestPageData(
    val questionId: QuestionDto,
    var selected: Int,
    val radioNumber:Int
)
