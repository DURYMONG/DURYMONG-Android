package com.example.durymong.view.column

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.durymong.databinding.FragmentColumnDetailBinding
import com.example.durymong.util.getSafeParcelable
import com.example.durymong.view.column.viewmodel.ColumnViewModel

class ColumnDetailFragment: Fragment() {
    private var _binding: FragmentColumnDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ColumnViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColumnDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //전달받은 Column 객체를 사용하여 UI 업데이트
        viewModel.columnData.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.ivColumnImg)
            binding.tvColumnHeadline.text = it.subtitle
            binding.tvColumnTitle.text = it.title
            binding.tvColumnBody.text = it.content
        }

        binding.ivTopAppBarBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}