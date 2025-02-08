package com.example.durymong.view.do_it.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.durymong.databinding.FragmentDoItMonthlyRecordBinding
import com.example.durymong.view.do_it.record.adapter.RVAdapterMonthlyRecord
import com.example.durymong.view.do_it.record.viewmodel.MonthlyRecordViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MonthlyRecordFragment: Fragment() {
    private var _binding: FragmentDoItMonthlyRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapterMonthlyRecord: RVAdapterMonthlyRecord

    private val viewModel: MonthlyRecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItMonthlyRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRVAdapterMonthlyRecord()
        observeViewModel()
        onChangeMonthButton()
    }

    private fun observeViewModel() {
        viewModel.monthlyRecordList.observe(viewLifecycleOwner) {
            rvAdapterMonthlyRecord.notifyDataSetChanged()
        }
        viewModel.currentMonth.observe(viewLifecycleOwner) {
            binding.tvCurrentMonthCalendar.text = "${it.monthValue}월 성장일지"
        }
    }

    private fun onChangeMonthButton(){
        binding.btnPrevMonth.setOnClickListener {
            viewModel.changeMonth(-1)
        }
        binding.btnNextMonth.setOnClickListener {
            viewModel.changeMonth(1)
        }
    }

    private fun initRVAdapterMonthlyRecord() {
        rvAdapterMonthlyRecord = RVAdapterMonthlyRecord(requireContext(), viewModel.monthlyRecordList) {
            //날짜에 해당하는 화면으로 이동
        }
        binding.rvCalendar.adapter = rvAdapterMonthlyRecord
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}