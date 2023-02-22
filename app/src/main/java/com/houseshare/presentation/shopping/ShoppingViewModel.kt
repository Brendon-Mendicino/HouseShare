package com.houseshare.presentation.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houseshare.domain.shopping.Shopping
import java.time.LocalDateTime
import java.util.*

class ShoppingViewModel : ViewModel() {

    private val _shoppingList: MutableLiveData<List<Shopping>> = MutableLiveData(

        listOf(
            "sium",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "sium",
            "sium",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
            "fratm",
        )
            .asSequence()
            .withIndex()
            .map {
                Shopping(
                    id = it.index,
                    creationDate = LocalDateTime.now(),
                    title = it.value,
                    isChecked = false
                )
            }
            .toList()
    )
    val shoppingList: LiveData<List<Shopping>> = _shoppingList

    private val _selectedShoppingList: MutableLiveData<SortedSet<Shopping>> = MutableLiveData(
        sortedSetOf(Comparator { o1, o2 -> o1.id - o2.id })
    )
    val selectedShoppingList: LiveData<SortedSet<Shopping>> = _selectedShoppingList


    fun removeShopping(shopping: Shopping) {
        _shoppingList.value?.toMutableList()?.let {
            if (it.remove(shopping)) {
                _shoppingList.value = it
            }
        }

        _selectedShoppingList.value?.let {
            if (it.remove(shopping)) {
                _selectedShoppingList.value = it
            }
        }
    }

    fun onShoppingToggle(shopping: Shopping, isChecked: Boolean) {
        val list = _shoppingList.value?.toMutableList() ?: return

        list.indexOfFirst { it == shopping }.let {
            if (it == -1) return

            val newShopping = list[it].run {
                copy(
                    creationDate = creationDate,
                    title = title,
                    isChecked = isChecked
                )
            }

            list[it] = newShopping

            _selectedShoppingList.value = _selectedShoppingList.value?.apply {
                if (isChecked) {
                    add(newShopping)
                } else {
                    remove(newShopping)
                }
            }

            _shoppingList.value = list
        }
    }
}