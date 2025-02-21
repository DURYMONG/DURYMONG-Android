package com.example.durymong.view.do_it

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.DisableContentCapture
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.durymong.R
import com.example.durymong.databinding.FragmentDoItBinding
import com.example.durymong.model.dto.request.doit.CheckActivityRequest
import com.example.durymong.view.do_it.test_page.model.TestMainPageViewModel
import com.example.durymong.view.do_it.viewmodel.DoItViewModel

class DoItFragment : Fragment() {
    private var _binding: FragmentDoItBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController : NavController

    private val viewModel: DoItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItBinding.inflate(layoutInflater)
        navController= findNavController()

        initMainPage()

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

        initMonthlyDiary()

        return binding.root
    }

    override fun onPause() {
        // 이 때 다시 호출해서 점 색칠하기
        super.onPause()
        viewModel.loadTestMainPage()
    }

    private fun initMainPage() {
        viewModel.doItMainPage.observe(viewLifecycleOwner){
            if(it!=null){
                Glide.with(this) // 메인 몽 이미지
                    .load(it.result.mongImage)
                    .into(binding.ivDoItMainMong)

                binding.cbDoItBedClean.isChecked =it.result.activityList[0].checked
                binding.cbDoItWalk.isChecked =it.result.activityList[1].checked
                binding.cbDoItStretching.isChecked =it.result.activityList[2].checked
                binding.cbDoItMeditation.isChecked =it.result.activityList[3].checked
            }
        }

    }

    private fun initMonthlyDiary() {
        binding.ivDoItCalender.setOnClickListener {
            navController.navigate(R.id.action_fragment_do_it_to_fragment_do_it_monthly_diary)
        }
    }


    private fun initCheckButton() {
        binding.cbDoItBedClean.setOnClickListener{
            if(binding.cbDoItBedClean.isChecked){
                viewModel.submitCheck(CheckActivityRequest(1))
            }
            else{
                viewModel.cancelCheck(1)
            }

        }
        binding.cbDoItWalk.setOnClickListener {
            if (binding.cbDoItWalk.isChecked) {
                viewModel.submitCheck(CheckActivityRequest(2))
            } else {
                viewModel.cancelCheck(2)
            }
        }
        binding.cbDoItStretching.setOnClickListener{
            if(binding.cbDoItStretching.isChecked){
                viewModel.submitCheck(CheckActivityRequest(3))
            }
            else{
                viewModel.cancelCheck(3)
            }

        }
        binding.cbDoItMeditation.setOnClickListener{
            if(binding.cbDoItMeditation.isChecked){
                viewModel.submitCheck(CheckActivityRequest(4))
            }
            else{
                viewModel.cancelCheck(4)
            }
        }
    }

    private fun initTciTest() {
        binding.cvDoItTciTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "범불안 장애 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initDepressionTest() {
        binding.cvDoItDepressionTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "조울증 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initSentenceTest() {
        binding.cvDoItSentenceCompletionTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "외상 후 스트레스 검사"
            )
            navController.navigate(action)
        }
    }

    private fun initPostStressTest() {
        binding.cvDoItPostTraumaticStressTest.setOnClickListener {
            val action = DoItFragmentDirections.actionFragmentDoItToFragmentLevelTest(
                testName = "우울증 검사"
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