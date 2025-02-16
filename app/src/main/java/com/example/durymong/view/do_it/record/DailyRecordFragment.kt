package com.example.durymong.view.do_it.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.durymong.databinding.FragmentDoItDailyRecordBinding
import com.example.durymong.view.do_it.record.viewmodel.MonthlyRecordViewModel

class DailyRecordFragment: Fragment() {
    private var _binding: FragmentDoItDailyRecordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MonthlyRecordViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDoItDailyRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.selectedDate.observe(viewLifecycleOwner) {
            val month = it.split("-")[1].toInt().toString()
            val day = it.split("-")[2].toInt().toString()
            Log.d("DailyRecordFragment", "Selected Date: ${month}월 ${day}일")
            binding.tvDate.text = "${month}월 ${day}일"
        }
        viewModel.mongName.observe(viewLifecycleOwner) {
            binding.tvSeeMongChatRecord.text = "${it}과의 대화 기록"
        }
        viewModel.mongImg.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it)
                .into(binding.ivMong)
        }
    }
}