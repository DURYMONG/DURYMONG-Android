package com.example.durymong.view.mong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.durymong.databinding.FragmentHomeBinding
import com.example.durymong.view.mong.viewmodel.HomeDataViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel.loadHomeData()

        viewModel.homeData.observe(viewLifecycleOwner, Observer { homeData ->
            homeData?.let {
                binding.tvHomeTodaysDate.text = it.date
                binding.tvHomeUntilToday.text = "${it.mongName}과 함께한지 ${it.withMongDate}일째"
                binding.tvHomeChatQuestion.text = it.mongQuestion

                if(it!=null) {
                    Glide.with(this)
                        .load(it.mongImage)
                        .into(binding.ivHomeCharacter)
                }
            }
        })

        binding.ivHomeSettings.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToFragmentSettings())
        }

        binding.clHomeAnswer.setOnClickListener {
            //TODO : editText 기능 구현
        }

        binding.clHomeGrowMong.setOnClickListener {
            //TODO : 두리몽 키우기 화면 추가, 연결
        }

        binding.clHomeChatRecord.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionFragmentHomeToFragmentHomeChat())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}