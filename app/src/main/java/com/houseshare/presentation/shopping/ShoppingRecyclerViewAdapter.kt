package com.houseshare.presentation.shopping

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.houseshare.databinding.FragmentShoppingItemBinding
import com.houseshare.domain.shopping.ShoppingItem

/**
 * [RecyclerView.Adapter] that can display a [ShoppingItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ShoppingRecyclerViewAdapter(
    private val values: List<ShoppingItem>,
) : RecyclerView.Adapter<ShoppingRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCheckedChangeListener = { isChecked ->

            }
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(
        val binding: FragmentShoppingItemBinding,
        val onCheckedChangeListener: (Boolean) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {


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

}