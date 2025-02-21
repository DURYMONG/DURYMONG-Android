package com.example.durymong.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSettingsEditPassword.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionFragmentSettingsToFragmentEditPassword())
        }
        binding.tvSettingsEditUserInfo.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionFragmentSettingsToFragmentEditUserInfo())
        }
        binding.tvSettingsSetAlarm.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionFragmentSettingsToFragmentAlarm())
        }
        binding.tvSettingsResign.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionFragmentSettingsToFragmentResign())
        }

        binding.ivSettingsBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}