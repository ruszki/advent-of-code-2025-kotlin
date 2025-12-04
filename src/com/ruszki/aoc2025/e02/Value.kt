package com.ruszki.aoc2025.e02

data class Value(val value: UInt) {
    fun isInvalid(): Boolean {
        val stringValue = value.toString()

        return if (stringValue.length.mod(2) == 1) {
            false
        } else {
            stringValue.subSequence(0, stringValue.length / 2) ==
                    stringValue.subSequence(stringValue.length / 2, stringValue.length)
        }
    }
}