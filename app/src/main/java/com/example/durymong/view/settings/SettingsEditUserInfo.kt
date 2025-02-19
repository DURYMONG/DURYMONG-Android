package com.example.durymong.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentSettingsEditUserInfoBinding
import com.example.durymong.view.settings.viewmodel.SettingsViewModel

class SettingsEditUserInfo : Fragment() {
    private var _binding: FragmentSettingsEditUserInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel : SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsEditUserInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSettingsEditUserInfoBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSettingsEditUserInfoDelete.setOnClickListener {
            //TODO 기록 지우기
        }

        binding.btnSettingsEditUserInfoLogout.setOnClickListener {
            val userName = binding.etSettingsEditUserInfoName.text.toString()
            val mongName = binding.etSettingsEditUserInfoMong.text.toString()

            if (userName.isBlank() || mongName.isBlank()) {
                Toast.makeText(requireContext(), "변경할 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.changeUserInfo(userName, mongName)
        }

        viewModel.changeUserInfoResult.observe(viewLifecycleOwner) { result ->
            val (success, message) = result
            if (success) {
                Toast.makeText(requireContext(), "회원 정보 변경 성공: $message", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "회원 정보 변경 실패: $message", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}