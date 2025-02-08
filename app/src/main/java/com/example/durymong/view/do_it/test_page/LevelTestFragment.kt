package com.example.durymong.view.do_it.test_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.durymong.databinding.FragmentLevelTestBinding

class LevelTestFragment : Fragment() {

    private var _binding: FragmentLevelTestBinding? = null
    private val binding get() = _binding!!

    private val args: TestPageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLevelTestBinding.inflate(layoutInflater)

        initTestName()

        initBackButton()

        initStartTest()

        return binding.root
    }

    private fun initTestName() {
        binding.tvDoItStressNameTop.text = args.testName
        binding.tvDoItStressTestName.text = args.testName
        binding.tvDoItStressTestName2.text = args.testName

        // 추가적으로 작업예정
    }

    private fun initStartTest() {
        binding.ivDoItStressBottom.setOnClickListener {
            val action = LevelTestFragmentDirections.actionLevelTestFragmentToTestPageFragment(args.testName)
            findNavController().navigate(action)
        }
    }

    private fun initBackButton() {
        binding.ivDoItStressBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}