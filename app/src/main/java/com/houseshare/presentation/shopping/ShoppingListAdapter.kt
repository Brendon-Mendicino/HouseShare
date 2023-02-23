package com.houseshare.presentation.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.houseshare.databinding.FragmentShoppingItemBinding
import com.houseshare.domain.shopping.Shopping
import java.time.format.DateTimeFormatter

class ShoppingListAdapter(
    private val onToggleListener: OnToggleListener
) :
    ListAdapter<Shopping, ShoppingListAdapter.ShoppingViewHolder>(ShoppingDiffCallback) {


    object ShoppingDiffCallback : DiffUtil.ItemCallback<Shopping>() {
        override fun areItemsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Shopping, newItem: Shopping): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            FragmentShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        return holder.bind(getItem(position), onToggleListener::onToggle)
    }

    inner class ShoppingViewHolder(
        private val binding: FragmentShoppingItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Shopping, onToggleListener: (Shopping, Boolean) -> Unit) {
            binding.avatar.letter.text = model.title.first().uppercase()
            binding.title.text = model.title
            binding.checkbox.isChecked = model.isChecked
            binding.checkbox.setOnClickListener {
                onToggleListener(model, (it as MaterialCheckBox).isChecked)
            }
            binding.date.text = DateTimeFormatter
                .ofPattern("dd MMM yyyy")
                .format(model.creationDate)
        }
    }
}

fun interface OnToggleListener {
    fun onToggle(shopping: Shopping, isChecked: Boolean)
}
