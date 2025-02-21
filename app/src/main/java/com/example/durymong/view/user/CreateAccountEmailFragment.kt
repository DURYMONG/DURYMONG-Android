package com.example.durymong.view.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.durymong.R
import com.example.durymong.databinding.FragmentCreateAccountEmailBinding
import com.example.durymong.databinding.FragmentLoginBinding
import com.example.durymong.view.user.viewmodel.AuthViewModel

class CreateAccountEmailFragment :  Fragment() {

    private var _binding: FragmentCreateAccountEmailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    private var isEmailVerified = false // 이메일 인증 성공 여부
    private var isAuthCodeSent = false // 이메일 인증번호 전송 여부
    private lateinit var userId: String
    private lateinit var email: String // 입력한 이메일 저장

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountEmailBinding.inflate(inflater, container, false)

        arguments?.let {
            userId = it.getString("userId", "") ?: ""
            Log.d("데이터 전달", "아이디값 email fragment로 전달 완료")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 인증번호 입력 창 숨김
        binding.clCreateAccountEmailVerification.visibility = View.GONE

        // 다음 버튼 클릭 이벤트
        binding.btnCreateAccountEmailNext.setOnClickListener {
            handleNextButtonClick()
        }
    }

    //다음 버튼 클릭 처리

    private fun handleNextButtonClick() {
        email = binding.etCreateAccountEmail.text.toString().trim()

        when {
            email.isEmpty() -> {
                Toast.makeText(requireContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

            !isAuthCodeSent -> {
                checkEmailDuplicate()
            }

            !isEmailVerified -> {
                verifyAuthCode()
            }
        }
    }

    // 이메일 중복 검사
    private fun checkEmailDuplicate() {
        viewModel.checkEmailDuplicate(email)

        viewModel.emailCheckResult.observe(viewLifecycleOwner) { result ->
            if (result.success) {
                Toast.makeText(requireContext(), "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT).show()
                sendAuthCode()
            } else {
                Toast.makeText(requireContext(), "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 이메일 인증번호 전송
    private fun sendAuthCode() {
        viewModel.requestEmailAuthCode(email)

        viewModel.emailAuthRequestResult.observe(viewLifecycleOwner) { result ->
            if (result.success) {
                isAuthCodeSent = true
                binding.clCreateAccountEmailVerification.visibility = View.VISIBLE // 인증번호 입력창 보이기
            } else {
                Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 인증번호 확인
    private fun verifyAuthCode() {
        val authCode = binding.etCreateAccountEmailVerification.text.toString().trim()

        if (authCode.isEmpty()) {
            Toast.makeText(requireContext(), "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.verifyEmailAuthCode(email, authCode)

        viewModel.emailAuthVerificationResult.observe(viewLifecycleOwner) { result ->
            if (result.success) {
                isEmailVerified = true
                Toast.makeText(requireContext(), "이메일 인증 성공!", Toast.LENGTH_SHORT).show()
                navigateToNext()
            } else {
                Toast.makeText(requireContext(), "알맞지 않은 인증번호입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //다음 화면으로 이동
    private fun navigateToNext() {
        val bundle = Bundle().apply {
            putString("userId", userId) // userId 전달
            putString("email", email) // email 전달
        }
        if (isAdded) {
            findNavController().navigate(R.id.action_createAccountEmail_to_createAccountPassword, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}