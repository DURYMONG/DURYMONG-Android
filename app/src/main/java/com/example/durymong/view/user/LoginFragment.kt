package com.example.durymong.view.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.durymong.MainActivity
import com.example.durymong.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)

        initButtons()
        checkLogin()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initButtons(){
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
    }

}