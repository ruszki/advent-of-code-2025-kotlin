package com.ruszki.aoc2025.e06

class Problem {
    var problemType = ProblemType.ADDITION

    private val numbers = mutableListOf<ULong>()

    fun result(): ULong {
        return numbers.reduce(problemType.reducer)
    }

    fun addNumber(numString: String, numberAdder: (MutableList<ULong>, String) -> Unit) {
        numberAdder(numbers, numString)
    }

    override fun toString(): String {
        return numbers.joinToString(" ${problemType.separator} ") + " = " + result()
    }


}