package com.ruszki.aoc2025.e02

data class Range(val startValue: ULong, val endValue: ULong) {
    fun simpleInvalidSum(): ULong {
        return invalidSum { it.isSimpleInvalid() }
    }

    private fun invalidSum(processor: (Value) -> Boolean): ULong {
        var invalidSum = 0uL
        var currentValue = startValue

        while (endValue >= currentValue) {
            val isInvalid = processor(Value(currentValue))

            if (isInvalid) {
                invalidSum += currentValue
            }

            currentValue++
        }

        return invalidSum
    }
}