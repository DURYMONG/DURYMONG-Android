package com.example.durymong.view.do_it.record

import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.durymong.databinding.FragmentDoItDailyDiaryDisabledBinding
import com.example.durymong.view.do_it.viewmodel.DoItViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DailyDiaryDisabledFragment : Fragment() {
    private var _binding: FragmentDoItDailyDiaryDisabledBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DoItViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoItDailyDiaryDisabledBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initPage()
        initDiaryInput()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun observeViewModel() {
        viewModel.mongImg.observe(viewLifecycleOwner) {
            Log.d("DailyRecordFragment", "Mong Image: $it")
            val requestOptions = RequestOptions()
                .timeout(10000)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 사용
            Glide.with(requireContext())
                .load(it)
                .apply(requestOptions)
                .into(binding.ivMong)
        }

        viewModel.diary.observe(viewLifecycleOwner) {
            Log.d("DailyDiaryFragment", "Diary: $it")
            binding.etDiary.setText(it)
        }
    }

    private fun initPage(){
        val dateTextColor = binding.tvDate.currentTextColor
        val dateText = SimpleDateFormat("M월 d일", Locale.getDefault())
            .format(Calendar.getInstance().time)

        val spannableString = SpannableString(dateText)

        //색상 적용
        spannableString.setSpan(
            ForegroundColorSpan(dateTextColor),
            0,
            dateText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //밑줄 적용
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            dateText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvDate.text = spannableString

        binding.ivTopAppBarBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun initDiaryInput(){
        // EditText의 최대 글자 수를 100자로 제한
        binding.etDiary.filters = arrayOf(InputFilter.LengthFilter(100))
        binding.etDiary.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.etDiary.addTextChangedListener(object : TextWatcher {
            private var isEditing = false  // 무한 루프 방지

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty() || isEditing) return

                isEditing = true // 변경 중 플래그 설정
                val cursorPosition = binding.etDiary.selectionStart // 현재 커서 위치 저장

                // 모든 문자에 밑줄 적용 (한글, 영어, 숫자, 특수문자 포함)
                s.setSpan(
                    UnderlineSpan(),
                    0,
                    s.length,
                    SpannableString.SPAN_INCLUSIVE_INCLUSIVE
                )

                // 원래 커서 위치 복원
                val newCursorPosition = if (cursorPosition <= s.length) cursorPosition else s.length
                binding.etDiary.setSelection(newCursorPosition)

                isEditing = false // 변경 완료 후 플래그 해제
            }
        })
    }
}