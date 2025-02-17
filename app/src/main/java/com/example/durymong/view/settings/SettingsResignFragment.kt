package com.example.durymong.view.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentSettingsBinding
import com.example.durymong.databinding.FragmentSettingsResignBinding

class SettingsResignFragment : Fragment() {
    private var _binding: FragmentSettingsResignBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsResignBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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