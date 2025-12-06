package com.ruszki.aoc2025.e06

import java.io.File
import java.math.BigInteger

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

    override fun toString(): String {
        return problems.joinToString("\n") { it.toString() } + "\n\nThe sum is ${result()}"
    }

    companion object {
        fun load(pathString: String, numberAdder: (MutableList<ULong>, String) -> Unit): MathHomework {
            val mathHomework = MathHomework()

            val operators = ProblemType.entries.associateBy { it.separator }

            val lines = File(pathString).readLines()

            val operatorColumns = Regex("[*+]\\s*").findAll(lines.last { it.isNotBlank() }).map { it.value }.toList()
            val columnSizes = operatorColumns.mapIndexed { index, column -> column.length - (if (index == operatorColumns.lastIndex) 0 else 1) }
            val columnRanges = mutableListOf(0..<columnSizes[0])

            for (columnSize in columnSizes.subList(1, columnSizes.size)) {
                val startIndex = columnRanges.last().last + 2
                val endIndex = startIndex + columnSize

                columnRanges.add(startIndex..<endIndex)
            }

            lines.forEach { line ->
                if (line.isNotBlank()) {
                    val values = columnRanges.map { columnRange -> line.substring(columnRange) }

                    if (values.size > mathHomework.problems.size) {
                        repeat(values.size - mathHomework.problems.size) {
                            mathHomework.addProblem()
                        }
                    }

                    if (values.isNotEmpty() && values[0].contains(Regex("\\d+"))) {
                        values.forEachIndexed { index, value ->
                            mathHomework.problems[index].addNumber(value, numberAdder)
                        }
                    } else {
                        values.forEachIndexed { index, value ->
                            mathHomework.problems[index].problemType = operators[value.trim()]!!
                        }
                    }
                }
            }

            return mathHomework
        }
    }
}