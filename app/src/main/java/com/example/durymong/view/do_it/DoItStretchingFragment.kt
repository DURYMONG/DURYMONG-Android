package com.example.durymong.view.do_it

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.durymong.R
import com.example.durymong.databinding.FragmentDoItStretchingBinding


class DoItStretchingFragment : Fragment() {

    private var _binding: FragmentDoItStretchingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDoItStretchingBinding.inflate(layoutInflater)
        initBack()

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun initBack() {
        binding.ivDoItStretchingBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}