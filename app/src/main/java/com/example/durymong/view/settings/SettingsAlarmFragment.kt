package com.example.durymong.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentSettingsAlarmBinding
import com.example.durymong.databinding.FragmentSettingsBinding

class SettingsAlarmFragment : Fragment() {
    private var _binding: FragmentSettingsAlarmBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsAlarmBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*        binding.ivSettingsBack.setOnClickListener {
            findNavController().popBackStack()
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}