package com.example.durymong.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.durymong.databinding.FragmentSettingsBinding
import com.example.durymong.databinding.FragmentSettingsResignBinding
import com.example.durymong.view.settings.viewmodel.SettingsViewModel

class SettingsResignFragment : Fragment() {
    private var _binding: FragmentSettingsResignBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsResignBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUserEliminationInfo()

        viewModel.userEliminationInfo.observe(viewLifecycleOwner) { result ->
            binding.tvSettingsResignMongName.text = result.mongName
            binding.tvSettingsResignMongDate.text = "${result.withMongDate}일"
            binding.tvSettingsResignUserName.text = result.id
            Glide.with(this).load(result.mongImage).into(binding.ivSettingsResignCharacter)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        binding.ivSettingsResignBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSettingsResignBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSettingsResign.setOnClickListener {
            //TODO
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}