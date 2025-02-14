package com.example.durymong.view.do_it.record.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.durymong.databinding.ItemCalendarDayBinding
import com.example.durymong.model.dto.response.doit.DateInfo
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class RVAdapterMonthlyRecord(
    private val context: Context,
    private val items: List<DateInfo>,
    private val onItemClick: (DateInfo) -> Unit
) : RecyclerView.Adapter<RVAdapterMonthlyRecord.ViewHolder>() {

    private var currentDate: String = getCurrentDate()

    inner class ViewHolder(val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DateInfo) {
            binding.tvCalendarDate.text = item.date.split("-")[2].toInt().toString()
            binding.vCalendarCircle.alpha = if (item.count >= 3) {
                1f
            } else if (item.count >= 2) {
                0.7f
            } else if (item.count >= 1) {
                0.3f
            } else {
                0f
            }
            binding.itemDay.setOnClickListener {
                onItemClick(item)
            }
            if (item.date == currentDate) {
                binding.vCalendarSelected.visibility = View.VISIBLE
            } else {
                binding.vCalendarSelected.visibility = View.INVISIBLE
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = ItemCalendarDayBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (item.date.isEmpty()){
            holder.binding.tvCalendarDate.text = ""
            holder.binding.vCalendarCircle.visibility = View.INVISIBLE
            holder.binding.vCalendarSelected.visibility = View.INVISIBLE
        } else{
            holder.bind(item)
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}