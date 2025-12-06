package com.ruszki.aoc2025.e06

class MathHomework {
    private val problems = mutableListOf<Problem>()

    fun result(): ULong {
        return problems.sumOf { it.result() }
    }

    fun addProblem(): Problem {
        val problem = Problem()

        problems.add(problem)

        return problem
    }
}