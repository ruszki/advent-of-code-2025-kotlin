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

    companion object {
        fun load(): MathHomework {
            val mathHomework = MathHomework()

            val problem1 = mathHomework.addProblem()
            problem1.problemType = ProblemType.MULTIPLICATION
            problem1.addNumber(123uL)
            problem1.addNumber(45uL)
            problem1.addNumber(6uL)

            val problem2 = mathHomework.addProblem()
            problem2.problemType = ProblemType.ADDITION
            problem2.addNumber(328uL)
            problem2.addNumber(64uL)
            problem2.addNumber(98uL)

            val problem3 = mathHomework.addProblem()
            problem3.problemType = ProblemType.MULTIPLICATION
            problem3.addNumber(51uL)
            problem3.addNumber(387uL)
            problem3.addNumber(215uL)

            val problem4 = mathHomework.addProblem()
            problem4.problemType = ProblemType.ADDITION
            problem4.addNumber(64uL)
            problem4.addNumber(23uL)
            problem4.addNumber(314uL)

            return mathHomework
        }
    }
}