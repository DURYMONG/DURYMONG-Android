package com.example.durymong.view.do_it.test_page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.durymong.databinding.FragmentLevelTestBinding
import com.example.durymong.view.do_it.test_page.model.TestMainPageViewModel
import com.example.durymong.view.do_it.test_page.model.TestPageViewModel

class LevelTestFragment : Fragment() {

    private var _binding: FragmentLevelTestBinding? = null
    private val binding get() = _binding!!

    private val args: TestPageFragmentArgs by navArgs()

    private val viewModel: TestMainPageViewModel by activityViewModels()
    private var testId = 0

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
        when (args.testName) {
            "우울증 검사" -> testId = 1
            "외상 후 스트레스 검사" -> testId = 2
            "스트레스 수치 검사" -> testId = 3
            "조울증 검사" -> testId = 4
            "범불안 장애 검사" -> testId = 5
            else -> Log.d("TestMainPageViewModel", "테스트 네임 오타 발생" + args.testName)
        }
        viewModel.loadTestMainPage(testId)
        viewModel.testMainPage.observe(viewLifecycleOwner) { testMainPage ->
            testMainPage?.result?.let { result ->
                binding.apply {
                    tvDoItStressNameTop.text = args.testName
                    tvDoItStressTestName.text = args.testName
                    tvDoItStressTestName2.text = args.testName

                    tvDoItStressTestNameEng.text = result.testEnglishName
                    tvDoItStressTestMainSentence.text = result.content
                    tvDoItStressTestExplain.text = result.evaluationInfo

                    tvDoItQuestionNumber.text = "${result.countOfQuestions}문항"
                    tvDoItStressTime.text = "약 ${result.requiredTime}분"

                    result.lastTestDTO.let { lastTest ->
                        if (lastTest.date != null) {//
                            tvDoItStressTestRecentRecordDate.text = lastTest.date
                            tvDoItStressTestRecentRecordNameScore.text =
                                "${lastTest.nickName}님      ${lastTest.lastScore}점"
                        } else {
                            tvDoItStressTestRecentRecordDate.text = "최근 기록 없음"
                            tvDoItStressTestRecentRecordNameScore.text = ""
                        }

                    }
                }

            }
        }
    }

    private fun initStartTest() {
        binding.ivDoItStressBottom.setOnClickListener {
            val action =
                LevelTestFragmentDirections.actionLevelTestFragmentToTestPageFragment(args.testName)
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