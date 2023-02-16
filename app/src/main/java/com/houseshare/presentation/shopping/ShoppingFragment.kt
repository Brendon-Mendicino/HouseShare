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

const val TAG = "ShoppingFragment"

/**
 * A fragment representing a list of Items.
 */
class ShoppingFragment : Fragment() {

    private val viewModel: ShoppingViewModel by viewModels()

    private val testList = listOf(
        ShoppingItem("sium"),
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
    ): View? {
        _binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ShoppingRecyclerViewAdapter(testList)
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

    companion object {

        @JvmStatic
        fun newInstance() =
            ShoppingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}