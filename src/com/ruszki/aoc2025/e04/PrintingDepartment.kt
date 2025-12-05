package com.ruszki.aoc2025.e04

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class PrintingDepartment {
    var freePaperRollCountAtStart: ULong = 0uL
        private set

    var freePaperRollSum: ULong = 0uL
        private set

    companion object {
        fun load(pathString: String): PrintingDepartment {
            val printingDepartment = PrintingDepartment()

            val gridLines = mutableListOf<GridLine>()

            var previousGridLine: GridLine? = null
            var currentGridLine: GridLine? = null

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (line.isNotBlank()) {
                        printingDepartment.freePaperRollCountAtStart += previousGridLine?.getFreeRollCount() ?: 0uL

                        previousGridLine = currentGridLine
                        currentGridLine = GridLine()

                        gridLines.add(currentGridLine)

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

            printingDepartment.freePaperRollCountAtStart += previousGridLine?.getFreeRollCount() ?: 0uL
            printingDepartment.freePaperRollCountAtStart += currentGridLine?.getFreeRollCount() ?: 0uL

            var previouslyRemovedPaperRollCount = 0uL

            while (previouslyRemovedPaperRollCount < printingDepartment.freePaperRollCountAtStart || previouslyRemovedPaperRollCount != printingDepartment.freePaperRollSum) {
                previouslyRemovedPaperRollCount = printingDepartment.freePaperRollSum

                for (gridLine in gridLines) {
                    printingDepartment.freePaperRollSum += gridLine.removeFreeRolls()
                }
            }

            return printingDepartment
        }
    }
}