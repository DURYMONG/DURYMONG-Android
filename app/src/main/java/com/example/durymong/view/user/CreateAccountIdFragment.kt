package com.example.durymong.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.durymong.R
import com.example.durymong.databinding.FragmentCreateAccountIdBinding
import com.example.durymong.model.repository.UserRepository
import com.example.durymong.view.user.viewmodel.AuthViewModel


class CreateAccountIdFragment : Fragment() {

    private var _binding: FragmentCreateAccountIdBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAccountIdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateAccountIdNext.setOnClickListener {
            val id = binding.etCreateAccountId.text.toString()
            if (id.isNotEmpty()) {
                viewModel.checkUserId(id)
            } else {
                binding.etCreateAccountId.error = "아이디를 입력해주세요."
            }
        }

        viewModel.checkUserIdResult.observe(viewLifecycleOwner, Observer { response ->
            if (response.success) {
                val bundle = Bundle().apply {
                    putString("userId", binding.etCreateAccountId.text.toString()) // userId 전달
                }
                findNavController().navigate(R.id.action_createAccountId_to_createAccountEmail, bundle)
            } else {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}