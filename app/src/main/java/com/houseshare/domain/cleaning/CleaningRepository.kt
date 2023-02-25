package com.houseshare.domain.cleaning

import java.time.LocalDateTime
import kotlin.random.Random

class CleaningRepository {

    private fun nextWord(): String {
        return generateSequence { Random.nextInt().toChar() }
            .take(Random.nextInt(3, 10))
            .joinToString(separator = "") { it.toString() }
    }

    private val testCleaningList by lazy {
        generateSequence(1) { it + 1 }
            .map {
                Cleaning(
                    it,
                    LocalDateTime.now()..LocalDateTime.now(),
                    Random.nextBoolean(),
                    generateSequence { nextWord() }
                        .map { w -> CleaningUser(w, Random.nextBoolean()) }
                        .take(15)
                        .toList()
                )
            }
            .take(20)
            .toList()
            .reversed()
    }

    fun getCleaningList(): List<Cleaning> {
        return testCleaningList
    }
}