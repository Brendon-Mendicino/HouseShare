package com.houseshare.presentation.shopping

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houseshare.domain.shopping.Shopping
import com.houseshare.domain.shopping.ShoppingRepository
import java.util.*

class ShoppingViewModel(
    private val shoppingRepository: ShoppingRepository = ShoppingRepository()
) : ViewModel() {

    private val _shoppingList: MutableLiveData<List<Shopping>> =
        MutableLiveData(shoppingRepository.getShoppingList())
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

    fun removeShopping(shoppingList: List<Shopping>) {
        _shoppingList.value?.toMutableList()?.let {
            val list = it
            list.removeAll(shoppingList)
            _shoppingList.value = list
        }

        _selectedShoppingList.value?.let {
            val list = it
            list.removeAll(shoppingList.toSet())
            _selectedShoppingList.value = list
        }
    }

    fun onShoppingToggle(shopping: Shopping, isChecked: Boolean) {
        val list = _shoppingList.value?.toMutableList() ?: return

        list.indexOfFirst { it == shopping }.let {
            if (it == -1) return

            val newShopping = list[it].copy(isChecked = isChecked)

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