package com.example.durymong.view.do_it.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.durymong.R
import com.example.durymong.databinding.FragmentDoItMonthlyRecordBinding
import com.example.durymong.model.dto.response.doit.DateInfo
import com.example.durymong.view.do_it.record.adapter.RVAdapterMonthlyRecord
import com.example.durymong.view.do_it.viewmodel.DoItViewModel

class MonthlyRecordFragment: Fragment() {
    private var _binding: FragmentDoItMonthlyRecordBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapterMonthlyRecord: RVAdapterMonthlyRecord

    private val viewModel: DoItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItMonthlyRecordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateCurrentDate()
        observeViewModel()
        onChangeMonthButton()
        with(binding){
            ivTopAppBarBack.setOnClickListener{
                findNavController().popBackStack()
            }
            btnRecordDay.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_do_it_monthly_diary_to_fragment_do_it_daily_diary)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.monthlyRecordList.observe(viewLifecycleOwner) {
            initRVAdapterMonthlyRecord(it)
        }
        viewModel.currentMonth.observe(viewLifecycleOwner) {
            binding.tvCurrentMonthCalendar.text = "${it.monthValue}월 성장일지"
        }
        viewModel.nickname.observe(viewLifecycleOwner) {
            binding.tvUserName.text = it
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

    private fun initRVAdapterMonthlyRecord(monthlyRecordList: List<DateInfo>) {
        rvAdapterMonthlyRecord = RVAdapterMonthlyRecord(requireContext(), monthlyRecordList) {
            viewModel.updateSelectedDate(it)
            findNavController().navigate(R.id.action_fragment_do_it_monthly_diary_to_fragment_do_it_daily_record)
        }
        binding.rvCalendar.adapter = rvAdapterMonthlyRecord
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}