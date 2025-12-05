package com.ruszki.aoc2025.e04

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class PrintingDepartment {
    var freePaperRollCount: ULong = 0uL
        private set

    companion object {
        fun load(pathString: String): PrintingDepartment {
            val printingDepartment = PrintingDepartment()

            var previousGridLine: GridLine? = null
            var currentGridLine: GridLine? = null

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (line.isNotBlank()) {
                        printingDepartment.freePaperRollCount += previousGridLine?.getFreeRollCount() ?: 0uL
                        previousGridLine?.removePrevious()

                        previousGridLine = currentGridLine
                        currentGridLine = GridLine()

                        previousGridLine?.setNext(currentGridLine)

                        for (char in line) {
                            when (char) {
                                '.' -> currentGridLine.addEmptySpace()
                                '@' -> currentGridLine.addRoll()
                            }
                        }
                    }
                }
            }

            printingDepartment.freePaperRollCount += previousGridLine?.getFreeRollCount() ?: 0uL
            printingDepartment.freePaperRollCount += currentGridLine?.getFreeRollCount() ?: 0uL

            return printingDepartment
        }
    }
}