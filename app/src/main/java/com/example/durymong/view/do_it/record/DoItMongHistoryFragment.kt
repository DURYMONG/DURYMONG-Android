package com.example.durymong.view.do_it.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.durymong.databinding.FragmentDoItMongHistoryBinding
import com.example.durymong.view.do_it.viewmodel.DoItViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DoItMongHistoryFragment : Fragment() {
    private var _binding: FragmentDoItMongHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItMongHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPage()
        viewModel.fetchDailyMongChatHistory(viewModel.selectedDate.value!!)
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel(){
        viewModel.selectedMongChatHistory.observe(viewLifecycleOwner){ history ->
            Log.d("DoItMongHistoryFragment", "observeViewModel: $history")
            if (history.mongQuestion.isEmpty()){
                Log.d("DoItMongHistoryFragment", "observeViewModel: history is empty")
                binding.clMongChatHistoryContainer.visibility = View.GONE
                binding.tvNoHistory.visibility = View.VISIBLE
            } else{
                Log.d("DoItMongHistoryFragment", "observeViewModel: history is not empty")
//                val requestOptions = RequestOptions()
//                    .timeout(10000)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 사용
//                Glide.with(requireContext())
//                    .load(history.mongImg)
//                    .apply(requestOptions)
//                    .into(binding.ivMong)
                binding.tvMongChatText.text = history.mongQuestion
                binding.tvUserChatText.text = history.userAnswer
                binding.clMongChatHistoryContainer.visibility = View.VISIBLE
                binding.tvNoHistory.visibility = View.GONE
            }
        }
    }

    private fun initPage(){
        val selectedDateString =
            viewModel.selectedDate.value?.let {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .parse(it)
            }

        val dateText = selectedDateString?.let {
            SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())
                .format(it)
        }

        Log.d("DoItMongHistoryFragment", "selectedDate: $dateText")
        binding.tvDetailChatDate.text = dateText
        binding.ivTopAppBarBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

}