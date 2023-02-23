package com.houseshare.presentation.cleaning

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.houseshare.R
import com.houseshare.databinding.FragmentCleaningItemBinding
import com.houseshare.domain.cleaning.Cleaning
import java.time.format.DateTimeFormatter

class CleaningListAdapter :
    ListAdapter<Cleaning, CleaningListAdapter.CleaningViewHolder>(CleaningDiffCallback) {


    object CleaningDiffCallback : DiffUtil.ItemCallback<Cleaning>() {
        override fun areItemsTheSame(oldItem: Cleaning, newItem: Cleaning): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Cleaning, newItem: Cleaning): Boolean =
            oldItem == newItem
    }

    inner class CleaningViewHolder(
        private val binding: FragmentCleaningItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cleaning: Cleaning) {
            val context = binding.week.context
            val resources = binding.week.context.resources
            val firstDate = DateTimeFormatter
                .ofPattern("EEE dd MMM yyyy")
                .format(cleaning.referenceWeek.start)
            val lastDate = DateTimeFormatter
                .ofPattern("dd MMM yyyy")
                .format(cleaning.referenceWeek.endInclusive)

            binding.week.text = resources.getString(R.string.week_with_number, cleaning.id)
            binding.interval.text =
                resources.getString(
                    R.string.date_interval,
                    "$firstDate - $lastDate"
                )

            val (completed, color) = if (cleaning.isCompleted) {
                Pair(
                    resources.getText(R.string.completed),
                    ContextCompat.getColor(context, R.color.desaturated_green)
                )
            } else {
                Pair(
                    resources.getText(R.string.incomplete),
                    ContextCompat.getColor(context, R.color.desaturated_red)
                )
            }

            binding.completeIndicator.text = completed
            binding.completeIndicator.compoundDrawablesRelative.filterNotNull().forEach {
                it.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CleaningViewHolder {
        return CleaningViewHolder(
            FragmentCleaningItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CleaningViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}