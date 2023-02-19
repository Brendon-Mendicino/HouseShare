package com.houseshare.presentation.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.houseshare.databinding.FragmentShoppingListBinding
import com.houseshare.domain.shopping.ShoppingItem


/**
 * A fragment representing a list of Items.
 */
class ShoppingFragment : Fragment() {

    companion object {
        const val TAG = "ShoppingFragment"
        @JvmStatic
        fun newInstance() =
            ShoppingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private val viewModel: ShoppingViewModel by viewModels()

    private val testList = listOf(
        ShoppingItem("sium"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("sium"),
        ShoppingItem("sium"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
        ShoppingItem("fratm"),
    )

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ShoppingListAdapter().apply {
                submitList(testList)
            }
            addItemDecoration(
                MaterialDividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                ).apply {
                    isLastItemDecorated = false
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}