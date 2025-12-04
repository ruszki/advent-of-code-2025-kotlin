package com.ruszki.aoc2025.e03

data class Battery(val banks: List<UInt>) {
    companion object {
        fun from(s: String): Battery {
            val banks = s.map { it.toString().toUInt() }

            return Battery(banks)
        }
    }

    fun maximumJoltage(): UInt {
        var maximumTen = banks[0]
        var maximumOne = banks[1]

        for (i in 1..<banks.lastIndex) {
            if (banks[i] > maximumTen) {
                maximumTen = banks[i]
                maximumOne = banks[i + 1]
            } else if (banks[i + 1] > maximumOne) {
                maximumOne = banks[i + 1]
            }
        }

        return maximumTen * 10u + maximumOne
    }
}