package com.dicoding.habitapp.ui.random

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view
        fun bind(pageType: PageType, pageData: Habit) {
            val title = itemView.findViewById<TextView>(R.id.pager_tv_title)
            val startTime = itemView.findViewById<TextView>(R.id.pager_tv_start_time)
            val imageLabel = itemView.findViewById<ImageView>(R.id.pager_priority_level)
            val countTime = itemView.findViewById<TextView>(R.id.pager_tv_minutes)
            val btn = itemView.findViewById<Button>(R.id.btn_open_count_down)

            title.text = pageData.title
            startTime.text = pageData.startTime
            when (pageType) {
                PageType.HIGH -> imageLabel.setImageResource(R.drawable.ic_priority_high)
                PageType.LOW -> imageLabel.setImageResource(R.drawable.ic_priority_low)
                else -> imageLabel.setImageResource(R.drawable.ic_priority_medium)
            }
            countTime.text = pageData.minutesFocus.toString()
            btn.setOnClickListener {
                onClick(pageData)
            }
        }
    }
}
