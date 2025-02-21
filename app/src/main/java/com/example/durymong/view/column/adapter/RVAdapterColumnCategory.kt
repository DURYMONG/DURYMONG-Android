package com.example.durymong.view.column.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.compose.ui.layout.Layout
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.durymong.R
import com.example.durymong.databinding.ItemColumnCategoryBinding
import com.example.durymong.databinding.ItemColumnCategoryDescriptionBinding
import com.example.durymong.model.dto.response.column.Category
import com.example.durymong.util.dpToPx
import com.example.durymong.view.column.viewmodel.ColumnViewModel

class RVAdapterColumnCategory(
    private val context: Context,
    private val items: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<RVAdapterColumnCategory.ViewHolder>() {
    inner class ViewHolder(val binding: ItemColumnCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, position: Int) {
            val requestOptions = RequestOptions()
                .timeout(10000)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐시 사용
                .override(binding.ivColumnCategoryIcon.width, binding.ivColumnCategoryIcon.height)
            Glide.with(context)
                .load(item.image)
                .apply(requestOptions)
                .into(binding.ivColumnCategoryIcon)
            binding.columnCategoryName.text = item.name
            //카테고리 클릭
            binding.cardColumnCategory.setOnClickListener {
                onItemClick(item)
            }

            //카테고리 설명 클릭
            binding.ivColumnCategoryQuestionmark.setOnClickListener{
                if (position % 3 == 0){
                    //각 행의 첫 번째 아이템
                    showPopupWindow(it, item.detail, onLeft = false)
                } else{
                    showPopupWindow(it, item.detail, onLeft = true)
                }
            }
        }

        private fun showPopupWindow(anchor: View, description: String, onLeft: Boolean){
            val binding = ItemColumnCategoryDescriptionBinding.inflate(
                LayoutInflater.from(anchor.context)
            )
            binding.tvColumnCategoryDescription.text = description

            //좌우반전 처리
            if(onLeft){
                binding.ivColumnCategoryDescriptionBubble.scaleX = -1f
            } else{
                binding.ivColumnCategoryDescriptionBubble.scaleX = 1f
            }

            //popup 생성
            val popupWindow = PopupWindow(
                binding.root,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            //popup 위치 조정
            if(onLeft){
                val xOffsetPx = (-174).dpToPx(anchor.context)
                val yOffsetPx = (-10).dpToPx(anchor.context)
                popupWindow.showAsDropDown(anchor, xOffsetPx, yOffsetPx)
            } else{
                val xOffsetPx = 18.dpToPx(anchor.context)
                val yOffsetPx = (-10).dpToPx(anchor.context)
                popupWindow.showAsDropDown(anchor, xOffsetPx, yOffsetPx)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVAdapterColumnCategory.ViewHolder {
        val binding = ItemColumnCategoryBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        if (currentItem != null) {
            holder.bind(currentItem, position)
        }
    }
}