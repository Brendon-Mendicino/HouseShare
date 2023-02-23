package com.houseshare.presentation.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.houseshare.R
import com.houseshare.databinding.FragmentShoppingDeleteDialogBinding

class ShoppingDeleteDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentShoppingDeleteDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingDeleteDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteText.apply {
            val size = viewModel.selectedShoppingList.value?.size ?: 0
            val format = if (size > 1) " (${size})" else ""

            text = resources.getString(R.string.delete_with_number, format)
        }

        binding.deleteText.setOnClickListener {
            val selectedShopping =
                viewModel.selectedShoppingList.value?.toList() ?: return@setOnClickListener
            viewModel.removeShopping(selectedShopping)

            dismiss()
        }
    }
}