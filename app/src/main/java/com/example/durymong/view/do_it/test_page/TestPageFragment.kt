package com.example.durymong.view.do_it.test_page

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.durymong.R
import com.example.durymong.databinding.FragmentTestPageBinding
import com.example.durymong.model.dto.request.doit.SubmitTestRequestDto
import com.example.durymong.model.dto.request.doit.TestPageResponseData
import com.example.durymong.view.do_it.test_page.model.TestPageViewModel

class TestPageFragment : Fragment() {
    private var _binding: FragmentTestPageBinding? = null
    private lateinit var stressRVTestPageAdapter: RVTestPageAdapter

    private val binding get() = _binding!!

    private val viewModel: TestPageViewModel by activityViewModels()

    private var currentTestPage = 1
    private var lastTestPage = 1

    private var start = 0
    private var end = 0

    private var isEnd = false

    private val args: TestPageFragmentArgs by navArgs()

    private var testId =0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestPageBinding.inflate(layoutInflater)

        setTestData()

        initData()

        initRvAdapter(start, end)

        initBackButton()

        initNextButton()
        initBeforeButton()

        return binding.root
    }

    private fun setTestData() {
        when(args.testName){
            "우울증 검사"-> testId=1
            "외상 후 스트레스 검사" -> testId=2
            "스트레스 수치 검사" -> testId=3
            "조울증 검사" -> testId=4
            "범불안 장애 검사"-> testId=5
        }
        viewModel.loadTestPageData(testId)
        viewModel.testPageList.observe(viewLifecycleOwner){
            initData()
            initRvAdapter(start, end)
            initBackButton()

            initNextButton()
            initBeforeButton()
            Log.d("testResult","변화 감지")
        }
        binding.tvStressTestName.text = args.testName
    }

    private fun initBackButton() {
        binding.ivStressBack.setOnClickListener {
            if (!isEnd) {
                endDialog(this.requireContext())
            } else {
                findNavController().navigateUp()
            }

        }
    }

    private fun initBeforeButton() {
        binding.ivTestBefore.setOnClickListener {
            if (currentTestPage != 1) {
                currentTestPage--
                binding.tvTestCurrentPageNumber.text = currentTestPage.toString()
                start -= 6
                end = currentTestPage * 6
                initRvAdapter(start, end)
                binding.tvTestNextPage.text = "다음 페이지"

            }
        }
    }

    private fun initNextButton() {
        binding.ivTestNext.setOnClickListener {
            if (currentTestPage == lastTestPage) {
                var finish =true
                for(item in viewModel.testPageList.value!!){
                    if(item.selected==0){
                        item.showWarning=true
                        finish=false
                    }
                }
                if(finish){
                    showResultDialog(this.requireContext())
                    isEnd = true
                }
                else{
                    initRvAdapter(start, end)
                    showResultWarningDialog(this.requireContext())
                }
            } else {
                currentTestPage++
                binding.tvTestCurrentPageNumber.text = currentTestPage.toString()
                if (currentTestPage == lastTestPage) {
                    binding.tvTestNextPage.text = "완료 하기"
                    start += 6
                    end = viewModel.testPageList.value?.size ?: 0
                } else {
                    start += 6
                    end += 6
                }
                initRvAdapter(start, end)
            }
        }
    }

    private fun initData() {
        isEnd =false
        binding.tvStressTestNumber.text = "${viewModel.testPageList.value?.size} 문항"
        currentTestPage = 1
        viewModel.testPageList.value?.size?.let {
            lastTestPage = (it - 1) / 6 + 1
        }
        binding.tvTestCurrentPageNumber.text = currentTestPage.toString()
        binding.tvTestLastPageNumber.text = lastTestPage.toString()
        Log.d("data",lastTestPage.toString())

        if (currentTestPage == lastTestPage) {
            binding.tvTestNextPage.text = "완료 하기"
        }
        else{
            binding.tvTestNextPage.text=" 다음 페이지"
        }

        start = 0
        end = if (lastTestPage == 1) viewModel.testPageList.value?.size ?: 0 else 6
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRvAdapter(start: Int, end: Int) {
        stressRVTestPageAdapter = RVTestPageAdapter(
            viewModel.testPageList.value?.subList(start, end) ?: emptyList(),
            requireContext()
        )

        binding.rvStressTest.apply {
            adapter = stressRVTestPageAdapter
                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    private fun showResultWarningDialog(context: Context){
        val resultDialog = Dialog(context)
        resultDialog.setContentView(R.layout.dialog_test_warning)
        resultDialog.setCancelable(false)

        val finishButton = resultDialog.findViewById<ImageView>(R.id.iv_test_warning_finish)
        val backButton =resultDialog.findViewById<ImageView>(R.id.iv_test_warning_continue)
        finishButton.setOnClickListener {
            resultDialog.dismiss()
            findNavController().navigateUp()
        }
        backButton.setOnClickListener{
            resultDialog.dismiss()
        }

        resultDialog.show()
    }

    private fun showResultDialog(context: Context) {
        //이미 응답다 돼어 있음
        val testResultData = mutableListOf<TestPageResponseData>()

        if(viewModel.testPageList.value!=null){
            for (item in viewModel.testPageList.value!!){
                testResultData.add(TestPageResponseData(item.questionId.number,item.selected-1))
            }
        }
        val requestData =SubmitTestRequestDto(testResultData)

        viewModel.loadTestResult(testId,requestData)

        // 여기까지 데이터 불러오기



        val resultDialog = Dialog(context)
        resultDialog.setContentView(R.layout.dialog_test_result)
        resultDialog.setCancelable(false)

        val userName = resultDialog.findViewById<TextView>(R.id.tv_user_name)

        val okButton = resultDialog.findViewById<ImageView>(R.id.iv_result_ok)
        val backButton =resultDialog.findViewById<ImageView>(R.id.iv_result_back)
        val resultScore = resultDialog.findViewById<TextView>(R.id.tv_result_score)
        val resultText = resultDialog.findViewById<TextView>(R.id.tv_result_message)

        val resultText1 = resultDialog.findViewById<TextView>(R.id.tv_result_message_0_11)

        viewModel.testResult.observe(viewLifecycleOwner){
            userName.text=viewModel.testResult.value?.result?.nickName
            resultScore.text=viewModel.testResult.value?.result?.userScore.toString()+"점"
            resultText.text=viewModel.testResult.value?.result?.userResult
            resultText1.text=viewModel.testResult.value?.result?.scoreDistributionList

        }


        okButton.setOnClickListener {
            resultDialog.dismiss()
            findNavController().navigateUp()
        }
        backButton.setOnClickListener{
            resultDialog.dismiss()
        }

        resultDialog.show()
    }

    private fun endDialog(context: Context) {

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_test_end_warning)
        dialog.setCancelable(false)

        dialog.show()

        dialog.findViewById<ImageView>(R.id.iv_waring_back).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<ImageView>(R.id.iv_waring_end).setOnClickListener {
            findNavController().navigateUp()
            dialog.dismiss()
        }
    }


}