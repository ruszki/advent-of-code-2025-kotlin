package com.ruszki.aoc2025.e09

class ULongUtils {
    companion object {
        fun difference(a : ULong, b : ULong) : ULong {
            return if (a > b) {
                a - b
            } else {
                b - a
            }
        }
    }
}