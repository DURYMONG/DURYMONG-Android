package com.example.durymong.view.column

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.durymong.R
import com.example.durymong.databinding.FragmentColumnBinding
import com.example.durymong.view.column.adapter.RVAdapterColumnCategory
import com.example.durymong.view.column.viewmodel.ColumnViewModel

class ColumnFragment : Fragment() {
    private var _binding: FragmentColumnBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapterColumnCategory: RVAdapterColumnCategory

    //viewModel 선언
    private val viewModel: ColumnViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColumnBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initRVAdapterColumnCategory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRVAdapterColumnCategory() {
        //이후에 api 연결시 세부 내용은 아마도 변경될 예정
        rvAdapterColumnCategory =
            RVAdapterColumnCategory(requireContext(), viewModel.columnCategoryList) {
//                viewModel.fetchColumnData()

                //ColumnDetailFragment로 Column 객체 전달
                val columnData = viewModel.columnData.value
                    ?: throw IllegalStateException("Column data is null")
                val action =
                    ColumnFragmentDirections.actionFragmentColumnToFragmentColumnDetail(columnData)

                findNavController().navigate(action)
            }

        with(binding.rvColumnMenuList) {
            adapter = rvAdapterColumnCategory
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }
}