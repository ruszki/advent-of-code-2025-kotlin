package com.ruszki.aoc2025.e02

data class Range(val startValue: ULong, val endValue: ULong) {
    fun invalidSum(): ULong {
        var invalidSum = 0uL
        var currentValue = startValue

        while (endValue >= currentValue) {
            val isInvalid = Value(currentValue).isInvalid()

            if (isInvalid) {
                invalidSum += currentValue
            }

            currentValue++
        }

        return invalidSum
    }
}