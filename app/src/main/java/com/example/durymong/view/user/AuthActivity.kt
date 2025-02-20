package com.example.durymong.view.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.durymong.MainActivity
import com.example.durymong.databinding.ActivityDurymongMainBinding
import com.example.durymong.model.TokenManager
import com.example.durymong.view.user.viewmodel.AuthViewModel

class AuthActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDurymongMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (TokenManager.getAccessToken() != null){
            navigateToMain()
            return
        }

        binding = ActivityDurymongMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
    }

    private fun initButtons(){
        binding.btnDurymongMainLogin.setOnClickListener {
            // 임의로 테스트용 함수 호출, 이후에는 수정해서 사용
            navigateToLogin()
        }
        binding.btnDurymongMainCreateAccount.setOnClickListener {
            navigateToRegister()
        }
        binding.ivDurymongMainLogo.setOnClickListener {
            // 임의로 테스트용 함수 호출, 이후에는 수정해서 사용
            navigateToMain()
        }
    }

    private fun navigateToRegister() {
        // TODO: 회원가입 화면으로 이동
    }


    private fun navigateToLogin(){
        // TODO: 로그인 화면으로 이동
        loginTest()
    }

    private fun navigateToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun loginTest(){
        // 로그인 테스트
        viewModel.loginTest()
    }

}