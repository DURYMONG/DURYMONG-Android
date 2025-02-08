package com.example.durymong.view.do_it.walk

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.durymong.databinding.FragmentDoItWalkBinding
import com.example.durymong.view.do_it.walk.viewmodel.WalkViewModel

class DoItWalkFragment : Fragment(){

    private var _binding: FragmentDoItWalkBinding? = null
    private val binding get() = _binding!!

    private lateinit var circularProgressBar: ProgressBar

    private val viewModel : WalkViewModel by activityViewModels()
    private val args: DoItWalkFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItWalkBinding.inflate(layoutInflater)

        initName()


        initTimer()
        initTimerSetting()
        initViewModel()

        initBackButton()

        initTimerStart()
        initTimePlus()
        return binding.root
    }

    private fun initName() {
        binding.tvDoItTitle.text = args.activityName
        if(args.activityName=="30분 걷기"){
            binding.tvDoItWalkMusicText.text="좋아하는 노래와 함께 30분만 걸어볼까요?"
            binding.tvDoItWalkTip.text="TIP! 강가를 걷거나 주변 공원, 학교를 걸어도 좋아요!"
            binding.tvDoItBottomTip.text="간단한 걷기운동은 행복 호르몬을 분비하여 기분을 개선해 주고, 심리적인 안정감을 주는데 도움이 됩니다."
        }
        else{
            binding.tvDoItWalkMusicText.text="잔잔한 노래와 함께 눈을 지긋이 감아봐요"
            binding.tvDoItWalkTip.text="TIP! 가장좋아하는 곳에서, 짧은 시간동안만 시도해봐요!"
            binding.tvDoItBottomTip.text="명상은 고요히 눈을 감고 차분하게 마음속으로 깊이 생각하는 것으로, 마음을 훈련하는데에 도움이 됩니다."
        }
    }

    private fun initBackButton() {
        binding.ivDoItWalkBack.setOnClickListener {
            viewModel.sendData(0, 0) // 시간 초기화
            findNavController().navigateUp()
        }
    }

    private fun initTimePlus() {
        binding.ivDoItWalk30.setOnClickListener{
            viewModel.sendData(viewModel.timeData.value!!.first, viewModel.timeData.value!!.second+30)

        }
    }

    private fun initTimerStart() {
        binding.ivDoItWalkRunTimer.setOnClickListener {
            circularProgressBar = binding.pbDoItWalk
            val animator = ObjectAnimator.ofInt(circularProgressBar, "progress", 0, 3600).apply{
                duration = viewModel.timeData.value!!.first * 1000 * 3600 + viewModel.timeData.value!!.second * 1000 *60 // 1000=1초
                interpolator = LinearInterpolator() // 일정한 속도
            }
            animator.start()

        }
    }

    private fun initViewModel() {
        viewModel.timeData.observe(viewLifecycleOwner) {value->
            binding.tvDoItWalkTime.text = "${value.first}시간 ${value.second}분"
        }
    }


    private fun initTimerSetting() {
        val bottomSheet = TimerBottomSheet()
        binding.ivDoItWalkDownArrow.setOnClickListener {
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTimer() {


    }

}