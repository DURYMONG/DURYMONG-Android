package com.example.durymong.view.mong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.durymong.databinding.FragmentHomeChatBinding
import com.example.durymong.view.mong.adapter.ChatHistoryAdapter
import com.example.durymong.view.mong.viewmodel.HomeDataViewModel

class HomeChatFragment : Fragment() {
    private var _binding: FragmentHomeChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeDataViewModel by viewModels()
    private lateinit var chatAdapter: ChatHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeChatBinding.inflate(layoutInflater)

        binding.ivHomeChatBack.setOnClickListener {
/*            val action = HomeFragmentDirections.actionFragmentHomeToFragmentSettings()
            findNavController().navigate(action)*/
        }

        chatAdapter = ChatHistoryAdapter()
        binding.rvHomeChat.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeChat.adapter = chatAdapter

        viewModel.loadChatHistory()

        viewModel.chatHistory.observe(viewLifecycleOwner, Observer { chatHistory ->
            chatHistory?.let {
                chatAdapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}