package com.example.durymong.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentSettingsEditPasswordBinding
import com.example.durymong.view.settings.viewmodel.SettingsViewModel

class SettingsEditPasswordFragment : Fragment() {
    private var _binding: FragmentSettingsEditPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel : SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsEditPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSettingsEditPasswordBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSettingsEditPasswordDone.setOnClickListener {
            val nowPassword = binding.etSettingsEditPasswordCurrentPw.text.toString()
            val newPassword = binding.etSettingsEditPasswordNewPw.text.toString()

            if (nowPassword.isBlank() || newPassword.isBlank()) {
                Toast.makeText(requireContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.changePassword(nowPassword, newPassword)
        }

        viewModel.changePasswordResult.observe(viewLifecycleOwner) { result ->
            val (success, message) = result
            if (success) {
                Toast.makeText(requireContext(), "비밀번호 변경 성공: $message", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "비밀번호 변경 실패: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}