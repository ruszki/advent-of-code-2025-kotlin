package com.ruszki.aoc2025.e02

data class Range(val startValue: UInt, val endValue: UInt) {
    fun invalidCount(): UInt {
        var invalidCount = 0u
        var currentValue = startValue

        while (endValue >= currentValue) {
            val isInvalid = Value(currentValue).isInvalid()

            if (isInvalid) {
                invalidCount++
            }

            currentValue++
        }

        return invalidCount
    }
}