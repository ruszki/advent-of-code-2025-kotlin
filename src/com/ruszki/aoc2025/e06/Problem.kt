package com.ruszki.aoc2025.e06

class Problem {
    var problemType = ProblemType.ADDITION

    private val numbers = mutableSetOf<ULong>()

    fun result(): ULong {
        return numbers.reduce(problemType.reducer)
    }

    fun addNumber(num: ULong) {
        numbers.add(num)
    }

    override fun toString(): String {
        return numbers.joinToString(" ${problemType.separator} ") + " = " + result()
    }


}