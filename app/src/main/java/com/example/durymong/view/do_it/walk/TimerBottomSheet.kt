package com.example.durymong.view.do_it.walk

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.durymong.databinding.BottomSheetTimerBinding
import com.example.durymong.view.do_it.walk.viewmodel.WalkViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TimerBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetTimerBinding? = null
    private val binding get() = _binding!!

    private var hour =0
    private var minute =0

    private val viewModel : WalkViewModel by activityViewModels()


    // 밑에 네비게이션바가 안보임

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = BottomSheetTimerBinding.inflate(layoutInflater)

        initNumberPicker()
        initTimerButton()


        return binding.root
    }

    private fun initTimerButton() {

        binding.ivTimer30.setOnClickListener {
            minute+=30
            if(minute>59){
                minute-=60
                hour+=1
            }
            if(hour>24){
                hour=0
            }
            binding.npHour.value = hour
            binding.npMinute.value = minute
        }
        binding.ivTimer60.setOnClickListener {
            hour+=1
            if(hour>24){
                hour=0
            }
            binding.npHour.value = hour
        }
        binding.ivTimer90.setOnClickListener {
            minute+=30
            hour+=1
            if(minute>59){
                minute-=60
                hour+=1
            }
            if(hour>24){
                hour=0
            }
            binding.npHour.value = hour
            binding.npMinute.value = minute
        }

        binding.tvTimerReset.setOnClickListener {
            binding.npHour.value = 0
            binding.npMinute.value = 0
            hour=0
            minute=0
        }

        binding.tvTimerDone.setOnClickListener {
            viewModel.sendData(hour.toLong(),minute.toLong())
            dismiss()
        }

    }

    private fun initNumberPicker() {
        val numberPickerHour = binding.npHour
        val numberPickerMinute = binding.npMinute
        numberPickerHour.minValue = 0
        numberPickerHour.maxValue = 24
        numberPickerMinute.minValue = 0
        numberPickerMinute.maxValue = 59

        numberPickerHour.setOnValueChangedListener { _, _, newVal ->
            hour = newVal
            Log.d("hour", hour.toString())
        }

        numberPickerMinute.setOnValueChangedListener{ _, _, newVal ->
            minute = newVal
            Log.d("minute", minute.toString())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}