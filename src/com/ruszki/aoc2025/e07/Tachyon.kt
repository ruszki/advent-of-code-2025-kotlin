package com.ruszki.aoc2025.e07

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Tachyon(val usedSplitters: ULong) {
    companion object {
        fun load(pathString: String): Tachyon {
            var usedSplitters = 0uL
            var previousTachyonLevel: TachyonLevel? = null

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (line.isNotBlank()) {
                        val size = line.length.toULong()
                        var startIndex: ULong? = null
                        val splitters = mutableListOf<ULong>()

                        line.forEachIndexed { index, tachyonElementChar ->
                            when (tachyonElementChar) {
                                'S' -> startIndex = index.toULong()
                                '^' -> splitters.add(index.toULong())
                            }
                        }

                        val currentTachyonLevel = TachyonLevel(size, splitters, startIndex)

                        usedSplitters += currentTachyonLevel.addIncomingBeams(previousTachyonLevel?.getBeams() ?: emptyList())

                        previousTachyonLevel = currentTachyonLevel
                    }
                }
            }

            return Tachyon(usedSplitters)
        }
    }
}