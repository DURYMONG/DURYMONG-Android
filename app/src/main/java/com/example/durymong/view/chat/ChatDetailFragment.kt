package com.example.durymong.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.durymong.databinding.FragmentChatDetailBinding
import com.example.durymong.databinding.FragmentChatMainBinding
import com.example.durymong.view.chat.viewmodel.ChatBotViewModel

class ChatDetailFragment : Fragment() {
    private var _binding: FragmentChatDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatBotViewModel by viewModels()
    private val args: ChatDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatDetailBinding.inflate(inflater, container, false)

        // 전달받은 chatBotId로 API 호출
        viewModel.startChat(args.chatBotId)

        // API 응답을 UI에 반영
        viewModel.chatStartResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                Glide.with(this).load(it.result.chatBotImage).into(binding.ivChatDetailCounselorImage)
                binding.tvChatDetail1stQuestion.text = it.result.message
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
