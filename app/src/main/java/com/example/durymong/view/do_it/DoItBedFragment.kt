package com.example.durymong.view.do_it

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentDoItBedBinding

class DoItBedFragment : Fragment() {
    private var _binding: FragmentDoItBedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItBedBinding.inflate(layoutInflater)

        initBack()
        return binding.root
    }

    private fun initBack() {
        binding.ivDoItBedBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}