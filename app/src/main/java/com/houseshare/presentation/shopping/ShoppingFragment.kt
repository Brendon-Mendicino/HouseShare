package com.houseshare.presentation.shopping

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.houseshare.R
import com.houseshare.databinding.FragmentShoppingListBinding
import com.houseshare.presentation.util.getColorFromAttribute


/**
 * A fragment representing a list of Items.
 */
class ShoppingFragment : Fragment() {

    companion object {
        const val TAG = "ShoppingFragment"
    }

    private val viewModel: ShoppingViewModel by viewModels()

    private lateinit var listAdapter: ShoppingListAdapter

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")

        setupMenuProvider()

        listAdapter = ShoppingListAdapter { shopping, isChecked ->
            viewModel.onShoppingToggle(shopping, isChecked)
        }.apply {
            submitList(viewModel.shoppingList.value)
        }

        binding.shoppingList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            addItemDecoration(
                MaterialDividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                ).apply {
                    isLastItemDecorated = false
                }
            )
        }

        viewModel.shoppingList.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }

        val itemTouchHelper = ItemTouchHelper(SwipeHelperCallback())
        itemTouchHelper.attachToRecyclerView(binding.shoppingList)
    }


    private fun setupMenuProvider() {

        viewModel.selectedShoppingList.observe(viewLifecycleOwner) {
            requireActivity().invalidateMenu()
        }

        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {

                override fun onPrepareMenu(menu: Menu) {
                    val checkoutValue = viewModel.selectedShoppingList.value?.isNotEmpty() ?: false

                    menu.findItem(R.id.checkout).isVisible = checkoutValue
                }

                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.shopping_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.checkout -> true
                        else -> false
                    }
                }

            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class SwipeHelperCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition

            when (direction) {
                ItemTouchHelper.START -> viewModel.removeShopping(listAdapter.currentList[position])
            }
        }

        override fun onChildDraw(
            canvas: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            with(viewHolder.itemView) {
                context ?: return@with

                // prepare canvas
                canvas.clipRect(right + dX.toInt(), top, right, bottom)

                // set background
                val background =
                    ColorDrawable(context.getColorFromAttribute(com.google.android.material.R.attr.colorErrorContainer))

                background.setBounds(right + dX.toInt(), top, right, bottom)
                background.draw(canvas)

                // set icon
                val icon =
                    ContextCompat.getDrawable(context, R.drawable.ic_delete_24) ?: return@with

                val halfIcon = icon.intrinsicHeight / 2;
                val iconTop = top + ((bottom - top) / 2 - halfIcon)
                val iconLeft = right - halfIcon * 2

                icon.setBounds(
                    iconLeft - 16.dp,
                    iconTop,
                    right - 16.dp,
                    iconTop + icon.intrinsicHeight
                )
                icon.setTint(context.getColorFromAttribute(com.google.android.material.R.attr.colorOnErrorContainer))
                icon.draw(canvas)
            }


            super.onChildDraw(
                canvas,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
        }
    }

    val Int.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(),
            resources.displayMetrics
        ).toInt()

}