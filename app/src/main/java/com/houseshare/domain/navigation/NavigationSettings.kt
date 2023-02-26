package com.houseshare.domain.navigation

import androidx.annotation.IdRes
import com.houseshare.R
import kotlinx.serialization.Serializable

@Serializable
data class NavigationSettings(
    val destination: Destination = Destination.SHOPPING
)

enum class Destination {
    SHOPPING,
    CLEANING;

    @IdRes
    fun toId(): Int {
        return when (this) {
            SHOPPING -> R.id.shoppingFragment
            CLEANING -> R.id.cleaningFragment
        }
    }
}

fun Int.toDestination(): Destination? {
    return when (this) {
        R.id.shoppingFragment -> Destination.SHOPPING
        R.id.cleaningFragment -> Destination.CLEANING
        else -> null
    }
}
