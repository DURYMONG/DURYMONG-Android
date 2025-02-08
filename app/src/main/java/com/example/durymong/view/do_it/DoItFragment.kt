package com.example.durymong.view.do_it

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.DisableContentCapture
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.durymong.R
import com.example.durymong.databinding.FragmentDoItBinding

class DoItFragment : Fragment() {
    private var _binding: FragmentDoItBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItBinding.inflate(layoutInflater)
        navController= findNavController()

        initCheckButton()

        initBedClean()

        initWalk()
        initMeditation()

        initStretching()

        initPostStressTest()
        initSentenceTest()
        initStressTest()
        initDepressionTest()
        initTciTest()

        return binding.root
    }



    private fun initCheckButton() {
        // api 연결 해야함
        binding.rbDoItMeditation.isChecked = true

    }

    private fun initTciTest() {
        binding.cvDoItTciTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "TCI 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initDepressionTest() {
        binding.cvDoItDepressionTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "우울증 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initSentenceTest() {
        binding.cvDoItSentenceCompletionTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "문장 완성 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initPostStressTest() {
        binding.cvDoItPostTraumaticStressTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "외상후 스트레스 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initStretching() {
        binding.ivDoItStretching.setOnClickListener {
            navController.navigate(R.id.action_fragment_do_it_to_fragment_do_it_stretching)
        }
        binding.tvDoItStretching.setOnClickListener {
            navController.navigate(R.id.action_fragment_do_it_to_fragment_do_it_stretching)
        }
    }

    private fun initBedClean() {
        binding.ivDoItBedClean.setOnClickListener {
            navController.navigate(R.id.action_fragment_do_it_to_fragment_do_it_bed_clean)
        }
        binding.tvDoItBedClean.setOnClickListener {
            navController.navigate(R.id.action_fragment_do_it_to_fragment_do_it_bed_clean)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initWalk() {
        val action = DoItFragmentDirections.actionFragmentDoItToFragmentDoItWalk("30분 걷기")

        binding.tvDoItWalk.setOnClickListener{
            navController.navigate(action)
        }

        binding.ivDoItWalk.setOnClickListener {
            navController.navigate(action)
        }
    }
    private fun initMeditation() {
        val action = DoItFragmentDirections.actionFragmentDoItToFragmentDoItWalk("명상하기")

        binding.tvDoItMeditation.setOnClickListener{
            navController.navigate(action)
        }
        binding.ivDoItMeditation.setOnClickListener {
            navController.navigate(action)
        }
    }

    private fun initStressTest() {
        binding.cvDoItStressLevelTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "스트레스 수치 검사"
            )
            navController.navigate(action)
        }
    }
}