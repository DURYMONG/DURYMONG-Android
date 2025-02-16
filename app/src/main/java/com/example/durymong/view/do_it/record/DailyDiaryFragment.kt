package com.example.durymong.view.do_it.record

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentDoItDailyDiaryBinding

class DailyDiaryFragment: Fragment() {
    private var _binding: FragmentDoItDailyDiaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItDailyDiaryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateTextColor = binding.tvDate.currentTextColor
        val dateText = binding.tvDate.text.toString()
        val spannableString = SpannableString(dateText)

        //색상 적용
        spannableString.setSpan(
            ForegroundColorSpan(dateTextColor),
            0,
            dateText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //밑줄 적용
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            dateText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvDate.text = spannableString

        binding.ivTopAppBarBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}