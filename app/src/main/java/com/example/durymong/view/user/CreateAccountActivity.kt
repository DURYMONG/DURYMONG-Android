package com.example.durymong.view.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.durymong.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.ivCreateAccountBack.setOnClickListener {
            finish() // 뒤로 가기 버튼 클릭 시 현재 액티비티 종료
        }

        // TODO: 회원가입 프로세스 추가
    }
}