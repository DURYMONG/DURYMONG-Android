package com.example.durymong.view.do_it.test_page

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.durymong.R
import com.example.durymong.databinding.FragmentTestPageBinding
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
        // 무슨 테스트인지 넣어주는 부분
        binding.tvStressTestName.text = args.testName
        // 뷰모델로 넣을 생각하는 중인데 api 보고 결정할듯
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
                showResultDialog(this.requireContext())
                isEnd = true
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

        if (currentTestPage == lastTestPage) {
            binding.tvTestNextPage.text = "완료 하기"
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

    private fun showResultDialog(context: Context) {

        val resultDialog = Dialog(context)
        resultDialog.setContentView(R.layout.dialog_test_result)
        resultDialog.setCancelable(false)

        val userName = resultDialog.findViewById<TextView>(R.id.tv_user_name)

        val okButton = resultDialog.findViewById<ImageView>(R.id.iv_result_ok)
        val resultScore = resultDialog.findViewById<TextView>(R.id.tv_result_score)
        val resultText = resultDialog.findViewById<TextView>(R.id.tv_result_message)

        val resultText1 = resultDialog.findViewById<TextView>(R.id.tv_result_message_0_11)
        val resultText2 = resultDialog.findViewById<TextView>(R.id.tv_result_message_12_13)
        val resultText3 = resultDialog.findViewById<TextView>(R.id.tv_result_message_14_16)
        val resultText4 = resultDialog.findViewById<TextView>(R.id.tv_result_message_17_20)
        val resultText5 = resultDialog.findViewById<TextView>(R.id.tv_result_message_21)

        // 검사 마다 text 세팅 다시 해줘야함 .
        userName.text="?"


        var result =0
        for(i in viewModel.testPageList.value!!){
            result +=i.selected
        }
        resultScore.text=result.toString()

        if(result<=11){
            resultText.text=resultText1.text
        }
        if(result in 12..13){
            resultText.text=resultText2.text
        }
        if(result in 14..16){
            resultText.text=resultText3.text
        }
        if(result in 17..20){
            resultText.text=resultText4.text
        }
        if(result>=21) {
            resultText.text = resultText5.text
        }

        okButton.setOnClickListener {
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