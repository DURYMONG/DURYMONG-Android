package com.example.durymong.view.mong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.durymong.databinding.FragmentHomeChatBinding

class HomeChatFragment : Fragment() {
    private var _binding: FragmentHomeChatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeChatBinding.inflate(layoutInflater)

        binding.ivHomeChatBack.setOnClickListener {
/*            val action = HomeFragmentDirections.actionFragmentHomeToFragmentSettings()
            findNavController().navigate(action)*/
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}