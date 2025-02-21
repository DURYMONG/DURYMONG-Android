package com.example.durymong.view.user

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.durymong.R
import com.example.durymong.databinding.DialogCreateAccountCompleteBinding
import com.example.durymong.databinding.FragmentCreateAccountIdBinding
import com.example.durymong.databinding.FragmentCreateAccountPasswordBinding
import com.example.durymong.view.user.viewmodel.AuthViewModel
import kotlin.math.log

class CreateAccountPasswordFragment : Fragment() {
    private var _binding: FragmentCreateAccountPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    private lateinit var userId: String
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountPasswordBinding.inflate(inflater, container, false)

        arguments?.let {
            userId = it.getString("userId", "")
            email = it.getString("email", "")
            Log.d("회원가입 정보 전달", "${userId}, ${email}")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateAccountIdNext.setOnClickListener {
            password = binding.etCreateAccountPassword.text.toString()

            if (password.isNotEmpty()) {
                viewModel.checkPasswordValidity(password)
            } else {
                binding.etCreateAccountPassword.error = "비밀번호를 입력해주세요."
            }
        }

        viewModel.passwordCheckResult.observe(viewLifecycleOwner) { result ->
            Log.d("비밀번호 검사 결과", "성공 여부: ${result.success}, 메시지: ${result.message}")

            if (result.success) {
                Log.d("회원가입 실행", "비밀번호 검증 성공, 회원가입 시작")
                registerUser()
            } else {
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 회원가입 요청 API 호출
    private fun registerUser() {
        if (!::password.isInitialized || password.isEmpty()) {
            Log.e("회원가입 요청 오류", "비밀번호가 초기화되지 않음")
            return
        }

        Log.d("회원가입 요청", "회원가입 요청 시작: userId=$userId, email=$email, password=$password")

        viewModel.registerUser(userId, email, password)

        viewModel.registerResult.observe(viewLifecycleOwner) { result ->
            Log.d("회원가입 결과", "성공 여부: ${result.success}, 메시지: ${result.message}")

            if (result.success) {
                Toast.makeText(requireContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show()
                //showCompleteDialog()
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "회원가입 실패: ${result.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // 회원가입 완료 다이얼로그 표시
    private fun showCompleteDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = DialogCreateAccountCompleteBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false) // 다이얼로그 밖을 눌러도 닫히지 않도록 설정

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialogBinding.btnCreateAccountComplete.setOnClickListener {
            dialog.dismiss()

            // 현재 회원가입 액티비티 종료
            requireActivity().finish()

        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}