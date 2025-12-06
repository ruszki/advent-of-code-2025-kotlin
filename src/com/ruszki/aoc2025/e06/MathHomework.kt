package com.ruszki.aoc2025.e06

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

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
        fun load(pathString: String): MathHomework {
            val mathHomework = MathHomework()

            val operators = ProblemType.entries.associateBy { it.separator }

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    val values = line.split(Regex("\\s+")).filter { it.isNotBlank() }

                    if (values.size > mathHomework.problems.size) {
                        repeat(values.size - mathHomework.problems.size) {
                            mathHomework.addProblem()
                        }
                    }

                    if (values.isNotEmpty() && values[0].matches(Regex("\\d+"))) {
                        values.forEachIndexed { index, value ->
                            mathHomework.problems[index].addNumber(value.toULong())
                        }
                    } else {
                        values.forEachIndexed { index, value ->
                            mathHomework.problems[index].problemType = operators[value]!!
                        }
                    }
                }
            }

            return mathHomework
        }
    }
}