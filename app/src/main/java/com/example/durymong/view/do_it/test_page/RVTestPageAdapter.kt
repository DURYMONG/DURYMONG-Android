package com.example.durymong.view.do_it.test_page

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.durymong.databinding.ItemTestFiveBinding
import com.example.durymong.databinding.ItemTestFourBinding
import com.example.durymong.databinding.ItemTestThreeBinding
import com.example.durymong.view.do_it.test_page.model.TestPageData

class RVTestPageAdapter(
    private val itemList: List<TestPageData>,
    private val context: Context
) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val VIEW_TYPE_FOUR = 0
        private const val VIEW_TYPE_THREE = 1
        private const val VIEW_TYPE_FIVE = 2

    }

    inner class ThreeItemViewHolder(
        private val binding: ItemTestThreeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestPageData) {
            binding.tvQuestionName.text = item.questionId.question
            binding.rbTest1.setOnClickListener{
                item.selected = 1
            }
            binding.rbTest2.setOnClickListener{
                item.selected = 2
            }
            binding.rbTest3.setOnClickListener{
                item.selected = 3
            }

            when (item.selected) {
                0-> if(item.showWarning) {
                    binding.ivTestWarning.visibility= View.VISIBLE
                }
                1-> binding.rbTest1.isChecked = true
                2-> binding.rbTest2.isChecked = true
                3-> binding.rbTest3.isChecked = true
            }
        }
    }

    inner class FourItemViewHolder(
        private val binding: ItemTestFourBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestPageData) {
            binding.tvQuestionName.text = item.questionId.question
            binding.rbTest1.setOnClickListener{
                item.selected = 1
            }
            binding.rbTest2.setOnClickListener{
                item.selected = 2
            }
            binding.rbTest3.setOnClickListener{
                item.selected = 3
            }
            binding.rbTest4.setOnClickListener{
                item.selected = 4
            }
            when (item.selected) {
                0-> if(item.showWarning) {
                    binding.ivTestWarning.visibility= View.VISIBLE
                }
                1-> binding.rbTest1.isChecked = true
                2-> binding.rbTest2.isChecked = true
                3-> binding.rbTest3.isChecked = true
                4-> binding.rbTest4.isChecked = true
            }
        }
    }
    inner class FiveItemViewHolder(
        private val binding: ItemTestFiveBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TestPageData) {
            binding.tvQuestionName.text = item.questionId.question
            binding.rbTest1.setOnClickListener{
                item.selected = 1
            }
            binding.rbTest2.setOnClickListener{
                item.selected = 2
            }
            binding.rbTest3.setOnClickListener{
                item.selected = 3
            }
            binding.rbTest4.setOnClickListener{
                item.selected = 4
            }
            binding.rbTest5.setOnClickListener{
                item.selected = 5
            }
            when (item.selected) {
                0-> if(item.showWarning) {
                    binding.ivTestWarning.visibility= View.VISIBLE
                }
                1-> binding.rbTest1.isChecked = true
                2-> binding.rbTest2.isChecked = true
                3-> binding.rbTest3.isChecked = true
                4-> binding.rbTest4.isChecked = true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].radioNumber == 4) {
            VIEW_TYPE_FOUR
        } else if(itemList[position].radioNumber == 5){
            VIEW_TYPE_FIVE
        }else
            VIEW_TYPE_THREE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_THREE -> {
                val binding =
                    ItemTestThreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ThreeItemViewHolder(binding)
            }

            VIEW_TYPE_FOUR -> {
                val binding =
                    ItemTestFourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FourItemViewHolder(binding)
            }
            else ->{
                val binding =
                    ItemTestFiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FiveItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ThreeItemViewHolder -> {
                holder.bind(itemList[position])
            }
            is FiveItemViewHolder -> {
                holder.bind(itemList[position])
            }
            else -> {
                holder as FourItemViewHolder
                holder.bind(itemList[position])
            }
        }
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

}