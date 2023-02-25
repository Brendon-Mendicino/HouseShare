package com.houseshare.presentation.cleaning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.houseshare.R
import com.houseshare.databinding.CleaningInfoItemBinding
import com.houseshare.domain.cleaning.CleaningUser

class CleaningInfoListAdapter :
    ListAdapter<CleaningUser, CleaningInfoListAdapter.CleaningUserViewHolder>(
        CleaningInfoDiffCallback
    ) {

    object CleaningInfoDiffCallback : DiffUtil.ItemCallback<CleaningUser>() {
        override fun areItemsTheSame(oldItem: CleaningUser, newItem: CleaningUser): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CleaningUser, newItem: CleaningUser): Boolean =
            oldItem == newItem
    }


    inner class CleaningUserViewHolder(
        private val binding: CleaningInfoItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cleaningUser: CleaningUser) {
            val drawable =
                if (cleaningUser.hasCleaned) R.drawable.id_done_24 else R.drawable.ic_unpublished_24

            binding.name.text = cleaningUser.name
            binding.name.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, drawable, 0
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CleaningUserViewHolder {
        return CleaningUserViewHolder(
            CleaningInfoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CleaningUserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}