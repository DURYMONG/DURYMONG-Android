package com.example.durymong.view.do_it.record

import android.icu.util.Calendar
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentDoItChatHistoryCharacterBinding
import com.example.durymong.model.dto.response.doit.BotChatDto
import com.example.durymong.view.do_it.record.adapter.RVAdapterChatbotHistory
import com.example.durymong.view.do_it.viewmodel.DoItViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DoItChatHistoryCharacterFragment : Fragment() {
    private var _binding: FragmentDoItChatHistoryCharacterBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapterChatbotHistory: RVAdapterChatbotHistory

    private val viewModel: DoItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItChatHistoryCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel(){

    }

    private fun initRVAdapterChatbotHistory(chatbotHistory: List<BotChatDto>){

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