package com.houseshare.domain.shopping

import java.time.LocalDateTime

data class Shopping(
    val id: Int,
    val creationDate: LocalDateTime,
    val title: String,
    val isChecked: Boolean = false,
) {

}