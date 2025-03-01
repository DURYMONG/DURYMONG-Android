package com.example.durymong

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.durymong.databinding.ActivityMainBinding
import com.example.durymong.model.TokenManager
import com.example.durymong.retrofit.RetrofitObject
import com.example.durymong.retrofit.service.UserService
import com.example.durymong.view.user.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideStatusBar()

        showBottomNavigation()

        // 토큰 상태 관리
        TokenManager.accessTokenLiveData.observe(this, Observer { token ->
            if (token == null) {
                // 토큰이 null이면 로그아웃 처리
                val userService: UserService = RetrofitObject.createService()
//                userService.postLogout()
                val intent = Intent(this, AuthActivity::class.java).apply{
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                finish()
            }
        })
    }

    private fun hideStatusBar() {
        // 1) Window fitsSystemWindows 설정 해제
        WindowCompat.setDecorFitsSystemWindows(this.window, false)
        // 2) WindowInsetsControllerCompat 사용
        val insetsController = WindowInsetsControllerCompat(
            this.window,
            this.window.decorView
        )
        // 3) 상태바 바 숨기기
        insetsController.hide(WindowInsetsCompat.Type.statusBars())

        // 4) 스와이프로 시스템 바를 일시적으로 나타낼 수 있게 설정
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun showBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMain.setupWithNavController(navController)
        setBottomNavVisible(navController)
    }

    private fun setBottomNavVisible(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvMain.visibility = when (destination.id) {
                R.id.fragment_mong, R.id.fragment_chat, R.id.fragment_column, R.id.fragment_do_it-> View.VISIBLE
                else -> View.GONE
            }
        }
    }
}