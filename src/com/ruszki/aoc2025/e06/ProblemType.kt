package com.ruszki.aoc2025.e06

enum class ProblemType(val separator: String, val reducer: (ULong, ULong) -> ULong) {
    ADDITION("+", { a, b -> a + b }),
    MULTIPLICATION("*", { a, b -> a * b })
}