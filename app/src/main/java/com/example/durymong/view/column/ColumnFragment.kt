package com.example.durymong.view.column

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.durymong.R
import com.example.durymong.databinding.FragmentColumnBinding
import com.example.durymong.model.dto.response.column.Category
import com.example.durymong.model.dto.response.column.KeywordSearchColumn
import com.example.durymong.view.column.adapter.RVAdapterColumnCategory
import com.example.durymong.view.column.adapter.RVAdapterColumnSearchResult
import com.example.durymong.view.column.viewmodel.ColumnViewModel

class ColumnFragment : Fragment() {
    private var _binding: FragmentColumnBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvAdapterColumnCategory: RVAdapterColumnCategory
    private lateinit var rvAdapterColumnSearchResult: RVAdapterColumnSearchResult

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

        initSearchListener()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSearchListener() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                searchAction()
                true
            } else {
                false
            }
        }
        binding.searchIcon.setOnClickListener {
            searchAction()
        }
    }

    private fun searchAction(){
        if(binding.searchEditText.text.toString().trim().isEmpty()){
            // 검색어가 없을 때
            binding.rvColumnCategoryList.visibility = View.VISIBLE
            binding.rvColumnSearchResult.visibility = View.GONE
        } else{
            viewModel.searchColumnByKeyword(binding.searchEditText.text.toString().trim())
            binding.rvColumnCategoryList.visibility = View.GONE
            binding.rvColumnSearchResult.visibility = View.VISIBLE
        }
    }

    private fun initRVAdapterColumnCategory(categories: List<Category>) {
        Log.d("ColumnFragment", "initRVAdapterColumnCategory: $categories")
        rvAdapterColumnCategory =
            RVAdapterColumnCategory(requireContext(), categories) {
                viewModel.fetchColumnData(it.categoryId)
                findNavController().navigate(R.id.action_fragment_column_to_fragment_column_detail)
            }

        with(binding.rvColumnCategoryList) {
            adapter = rvAdapterColumnCategory
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun initRVAdapterColumnSearchResult(searchResults: List<KeywordSearchColumn>) {
        Log.d("ColumnFragment", "initRVAdapterColumnSearchResult: $searchResults")
        rvAdapterColumnSearchResult =
            RVAdapterColumnSearchResult(requireContext(), searchResults) {
                viewModel.fetchColumnData(it.categoryId)
                findNavController().navigate(R.id.action_fragment_column_to_fragment_column_detail)
            }

        with(binding.rvColumnSearchResult) {
            adapter = rvAdapterColumnSearchResult
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
    }

    private fun observeViewModel() {
        viewModel.columnCategoryList.observe(viewLifecycleOwner) { categories ->
            Log.d("ColumnFragment", "observeViewModel: $categories")
            if (categories.isNotEmpty()) {
                initRVAdapterColumnCategory(categories)
            }
        }
        viewModel.columnSearchResult.observe(viewLifecycleOwner) { searchResults ->
            Log.d("ColumnFragment", "observeViewModel: $searchResults")
            if (searchResults.isNotEmpty()) {
                initRVAdapterColumnSearchResult(searchResults)
            }
        }
    }
}