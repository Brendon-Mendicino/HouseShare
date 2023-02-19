package com.houseshare.presentation.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.houseshare.databinding.FragmentShoppingItemBinding
import com.houseshare.domain.shopping.ShoppingItem

class ShoppingListAdapter :
    ListAdapter<ShoppingItem, ShoppingListAdapter.ShoppingViewHolder>(ShoppingDiffCallback) {


    object ShoppingDiffCallback : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

    }

    inner class ShoppingViewHolder(
        val binding: FragmentShoppingItemBinding,
        val onCheckedChangeListener: (Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                onCheckedChangeListener(isChecked)
            }
        }

        fun bind(model: ShoppingItem) {
            binding.avatar.letter.text = model.title.first().uppercase()
            binding.title.text = model.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
            FragmentShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onCheckedChangeListener = {

            }
        )
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}