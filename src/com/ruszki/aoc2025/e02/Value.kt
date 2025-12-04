package com.ruszki.aoc2025.e02

data class Value(val value: ULong) {
    fun isSimpleInvalid(): Boolean {
        val stringValue = value.toString()

        return if (stringValue.length.mod(2) == 1) {
            false
        } else {
            stringValue.subSequence(0, stringValue.length / 2) ==
                    stringValue.subSequence(stringValue.length / 2, stringValue.length)
        }
    }

    fun isMultipleInvalid(): Boolean {
        val stringValue = value.toString()

        var currentLength = 1

        while (currentLength <= stringValue.length.div(2)) {
            val currentRepeatValue = stringValue.subSequence(0, currentLength)

            if (stringValue.length.mod(currentLength) == 0) {
                var currentMultiplier = 1

                while (currentMultiplier * currentLength < stringValue.length &&
                    stringValue.subSequence(
                        currentMultiplier * currentLength,
                        (currentMultiplier + 1) * currentLength
                    )
                        .equals(currentRepeatValue)) {
                    currentMultiplier++
                }

                if (currentMultiplier * currentLength == stringValue.length) {
                    return true
                }
            }

            currentLength++
        }

        return false
    }
}