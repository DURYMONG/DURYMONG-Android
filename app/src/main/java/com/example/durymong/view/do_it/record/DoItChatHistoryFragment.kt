package com.example.durymong.view.do_it.record

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.durymong.databinding.FragmentDoItChatHistoryBinding
import com.example.durymong.view.do_it.record.adapter.RVAdapterSymptoms
import com.example.durymong.view.do_it.record.adapter.RVAdapterTestRecommendation
import com.example.durymong.view.do_it.viewmodel.DoItViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DoItChatHistoryFragment : Fragment() {
    private var _binding: FragmentDoItChatHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapterSymptoms: RVAdapterSymptoms
    private lateinit var rvTestRecommendation: RVAdapterTestRecommendation

    private val viewModel: DoItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItChatHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPage()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRVSymptoms(symptomList: List<String>){
        Log.d("DoItChatHistoryFragment", "initRVSymptoms: $symptomList")
        rvAdapterSymptoms =
            RVAdapterSymptoms(requireContext(), symptomList)

        with(binding.rvSymptomList){
            adapter = rvAdapterSymptoms
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun initRVTestRecommendations(testList: List<String>){
        Log.d("DoItChatHistoryFragment", "initRVTestRecommendations: $testList")
        rvTestRecommendation =
            RVAdapterTestRecommendation(requireContext(), testList)

        with(binding.rvTests) {
            adapter = rvTestRecommendation
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }

    private fun observeViewModel(){
        viewModel.selectedChatBotHistory.observe(viewLifecycleOwner){
            Log.d("DoItChatHistoryFragment", "observeViewModel: $it")
            val chats = it.chatHistory[0]
            if(it != null){
                binding.tvNoChatbotHistory.visibility = View.GONE
                val requestOptions = RequestOptions()
                    .timeout(10000)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 사용
                // 증상 질문
                if(chats.greetingMessage?.isNotEmpty() == true){
                    Glide.with(requireContext())
                        .load(it.chatBotImage)
                        .apply(requestOptions)
                        .into(binding.ivChatbot)
                    binding.tvChatbotGreeting.text = chats.greetingMessage
                    val symptoms = mutableListOf<String>()
                    symptoms.addAll(chats.symptoms)
                    if(chats.additionalSymptom?.isNotEmpty() == true) symptoms.add(chats.additionalSymptom)
                    initRVSymptoms(symptoms.toList())
                } else {
                    binding.clGreetingBlock.visibility = View.GONE
                }
                // 증상 예측
                if (chats.botPredictionMessage?.isNotEmpty() == true){
                    Glide.with(requireContext())
                        .load(it.chatBotImage)
                        .apply(requestOptions)
                        .into(binding.ivChatbotPrediction)
                    binding.tvChatbotPrediction.text = chats.botPredictionMessage
                } else{
                    binding.clPredictionBlock.visibility = View.GONE
                }
                // 테스트 추천
                if (chats.testRecommendationMessage?.isNotEmpty() == true){
                    Glide.with(requireContext())
                        .load(it.chatBotImage)
                        .apply(requestOptions)
                        .into(binding.ivChatbotRecommendation)
                    binding.tvChatbotRecommendation.text = chats.testRecommendationMessage
                    initRVTestRecommendations(chats.recommendedTests)
                } else{
                    binding.clRecommendationBlock.visibility = View.GONE
                }
                // 하루 기록하기
                if (chats.diaryRecommendationMessage?.isNotEmpty() == true){
                    Glide.with(requireContext())
                        .load(it.chatBotImage)
                        .apply(requestOptions)
                        .into(binding.ivChatbotRecord)
                    binding.tvChatbotRecord.text = chats.diaryRecommendationMessage
                } else{
                    binding.clRecordBlock.visibility = View.GONE
                }
                // 마지막 대화
                if (chats.finalMessage?.isNotEmpty() == true){
                    Glide.with(requireContext())
                        .load(it.chatBotImage)
                        .apply(requestOptions)
                        .into(binding.ivChatbotFinal)
                    binding.tvChatbotFinal.text = chats.finalMessage
                } else{
                    binding.clFinalBlock.visibility = View.GONE
                }

            } else{
                binding.svChatScrollview.visibility = View.GONE
                binding.tvNoChatbotHistory.visibility = View.VISIBLE
            }
        }
    }

    private fun initPage(){
        val dateTextColor = binding.tvDate.currentTextColor

        val selectedDateString =
            viewModel.selectedDate.value?.let {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .parse(it)
            }
        val dateText = selectedDateString?.let {
            SimpleDateFormat("M월 d일", Locale.getDefault())
                .format(it)
        }

        val spannableString = SpannableString(dateText)

        //색상 적용
        if (dateText != null) {
            spannableString.setSpan(
                ForegroundColorSpan(dateTextColor),
                0,
                dateText.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        //밑줄 적용
        if (dateText != null) {
            spannableString.setSpan(
                UnderlineSpan(),
                0,
                dateText.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.tvDate.text = spannableString
    }
}