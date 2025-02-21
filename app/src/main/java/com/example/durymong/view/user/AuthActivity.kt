package com.example.durymong.view.user

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.durymong.MainActivity
import com.example.durymong.R
import com.example.durymong.databinding.ActivityDurymongMainBinding
import com.example.durymong.model.TokenManager
import com.example.durymong.util.SharedPreferencesHelper
import com.example.durymong.view.user.viewmodel.AuthViewModel

class AuthActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDurymongMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // SharedPreferences 초기화
        SharedPreferencesHelper.init(this)

        if (TokenManager.getAccessToken() != null){
            navigateToMain()
        } else {
            navigateToCreateMong()
        }
        return

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
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }


    private fun navigateToLogin(){
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_main, loginFragment) // LoginFragment를 FrameLayout에 추가
            .addToBackStack(null) // 뒤로 가기 가능하도록 설정
            .commit()
        //loginTest()
    }

    private fun navigateToMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun navigateToCreateMong() {
        //startActivity(Intent(this, CreateMongActivity::class.java))
        val mongName = "두리몽"
        val mongType = "tree"
        val mongColor = "purple"

        viewModel.createMong(mongName, mongType, mongColor)

        finish() // AuthActivity 종료
    }


/*    private fun loginTest(){
        // 로그인 테스트
        viewModel.loginTest()
    }*/

}