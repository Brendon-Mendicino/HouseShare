package com.houseshare.domain.cleaning

import java.time.LocalDateTime

data class Cleaning(
    val id: Int,
    val referenceWeek: ClosedRange<LocalDateTime>,
    val isCompleted: Boolean,
    val cleaningUsers: List<CleaningUser>,
)