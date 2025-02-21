package com.example.durymong.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.durymong.MainActivity
import com.example.durymong.databinding.FragmentLoginBinding
import com.example.durymong.model.TokenManager
import com.example.durymong.view.user.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding ?= null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)

        initButtons()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initButtons(){
        binding.ivLoginBack.setOnClickListener {
            Log.d("로그인 화면", "뒤로가기 눌림")
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            val userId = binding.etLoginId.text.toString().trim()
            val password = binding.etLoginPw.text.toString().trim()

            if (userId.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(userId, password)
        }
    }

    private fun login(userId: String, password: String) {
        viewModel.login(userId, password)

        viewModel.loginResult.observe(viewLifecycleOwner) { response ->
            if (response != null && response.success) {
                val token = response.result.accessToken
                val refreshToken = response.result.refreshToken

                TokenManager.saveToken(token)
                TokenManager.saveRefreshToken(refreshToken)

                Log.d("LoginFragment", "로그인 성공, 토큰 저장 완료")

                moveToMainActivity()
            } else {
                Toast.makeText(requireContext(), "로그인 실패: ${response?.message ?: "서버 오류"}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // AuthActivity 종료
    }
}

/*    private fun initButtons(){
        //TODO : 네비게이션 진행 중
        binding.ivLoginBack.setOnClickListener {
            Log.d("로그인 화면", "뒤로가기 눌림")
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnLogin.setOnClickListener {
            if (checkLogin()) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // AuthActivity 종료
            }
        }
    }

    private fun checkLogin(): Boolean {
        //TODO : 로그인 판단

        return true
    }*/
