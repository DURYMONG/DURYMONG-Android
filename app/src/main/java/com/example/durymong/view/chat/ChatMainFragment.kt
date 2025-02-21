package com.example.durymong.view.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.durymong.databinding.FragmentChatMainBinding
import com.example.durymong.model.dto.response.chat.ChatBot
import com.example.durymong.view.chat.viewmodel.ChatBotViewModel

class ChatMainFragment : Fragment(){
    private var _binding: FragmentChatMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatBotViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatMainBinding.inflate(layoutInflater)

        viewModel.loadChatBots()

        viewModel.chatBots.observe(viewLifecycleOwner, Observer { chatBots ->
            chatBots?.let { updateUI(it) }
        })
        initClickListeners()

        return binding.root
    }

    private fun updateUI(chatBots: List<ChatBot>) {
        if (chatBots.isNotEmpty()) {
            val dogBot = chatBots.find { it.name == "바둑이" }
            val catBot = chatBots.find { it.name == "나비" }
            val bearBot = chatBots.find { it.name == "곰고미" }

            dogBot?.let {
                binding.tvChatDogName.text = it.name
                binding.tvDogFeature1.text = it.mbti
                binding.tvDogFeature2.text = it.accent
                binding.tvDogFeature3.text = it.nickname
                binding.tvDogIntro.text = it.slogan
                Glide.with(this).load(it.image).into(binding.ivDog)
            }

            catBot?.let {
                binding.tvChatCatName.text = it.name
                binding.tvCatFeature1.text = it.mbti
                binding.tvCatFeature2.text = it.accent
                binding.tvCatFeature3.text = it.nickname
                binding.tvCatIntro.text = it.slogan
                Glide.with(this).load(it.image).into(binding.ivCat)
            }

            bearBot?.let {
                binding.tvChatBearName.text = it.name
                binding.tvBearFeature1.text = it.mbti
                binding.tvBearFeature2.text = it.accent
                binding.tvBearFeature3.text = it.nickname
                binding.tvBearIntro.text = it.slogan
                Glide.with(this).load(it.image).into(binding.ivBear)
            }
        }
    }


    private fun initClickListeners() {
        binding.clChatDogCounselor.setOnClickListener { navigateToChatDetail(1) }
        binding.clChatCatCounselor.setOnClickListener { navigateToChatDetail(2) }
        binding.clChatBearCounselor.setOnClickListener { navigateToChatDetail(3) }
    }

    private fun navigateToChatDetail(chatBotId: Int) {
        viewModel.startChat(chatBotId)

        viewModel.chatStartResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response != null && response.success) {
                val action = ChatMainFragmentDirections.actionChatMainToChatDetail(
                    chatBotId,
                    response.result.chatBotImage,
                    response.result.message,
                    response.result.chatSessionId
                )
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "챗봇 연결 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}