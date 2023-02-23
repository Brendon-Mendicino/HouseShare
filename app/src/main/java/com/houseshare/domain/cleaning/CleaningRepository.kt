package com.houseshare.domain.cleaning

import java.time.LocalDateTime
import kotlin.random.Random

class CleaningRepository {

    private val testCleaningList by lazy {
        generateSequence(1) { it + 1 }
            .map {
                Cleaning(it, LocalDateTime.now()..LocalDateTime.now(), Random.nextBoolean())
            }
            .take(20)
            .toList()
            .reversed()
    }

    fun getCleaningList(): List<Cleaning> {
        return testCleaningList
    }
}