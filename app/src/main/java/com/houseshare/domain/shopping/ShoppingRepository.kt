package com.houseshare.domain.shopping

import java.time.LocalDateTime

class ShoppingRepository {

    private val shoppingList: List<Shopping> = generateSequence(1) { it + 1 }
        .map {
            Shopping(
                it,
                LocalDateTime.now(),
                it.toString()
            )
        }
        .take(20)
        .toList()

    fun getShoppingList() : List<Shopping> {
        return shoppingList
    }
}