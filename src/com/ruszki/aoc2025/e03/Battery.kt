package com.ruszki.aoc2025.e03

import kotlin.math.max

data class Battery(val banks: List<UInt>) {
    companion object {
        fun from(s: String): Battery {
            val banks = s.map { it.toString().toUInt() }

            return Battery(banks)
        }
    }

    fun maximumJoltage(usedBanks: UInt): ULong {
        val maximums = Array(usedBanks.toInt()) { 0u }
        val indexes = Array(banks.size) { -1 }

        for (i in 0..<usedBanks.toInt()) {
            maximums[i] = banks[i]
            indexes[i] = i
        }

        for (i in 1..banks.lastIndex) {
            for (j in max(0, i - banks.lastIndex + usedBanks.toInt() - 1 ) ..<usedBanks.toInt()) {
                if (banks[i] > maximums[j] && indexes[i] !in 0..j) {
                    for (k in 0..<(usedBanks.toInt() - j)) {
                        maximums[j + k] = banks[i + k]
                        indexes[i + k] = j + k
                    }

                    break
                }
            }
        }

        return maximums.fold(0uL) { sum, max -> sum * 10uL + max }
    }
}