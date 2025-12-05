package com.ruszki.aoc2025.e01

import java.nio.file.Files
import java.nio.file.Paths

class Safe {
    var value: UInt = 50u
        private set

    var processMatchCount: UInt = 0u
        private set

    private fun rotate(rotation: Rotation) {
        value = when (rotation.direction) {
            Direction.RIGHT -> (value + rotation.distance).mod(100u)
            Direction.LEFT -> (value.toInt() - rotation.distance.toInt()).mod(100).toUInt()
        }
    }

    companion object {
        fun load(pathString: String, processor: (UInt, Rotation, UInt) -> UInt): Safe {
            val safe = Safe()

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    val previousValue = safe.value
                    val rotation = Rotation.from(line)

                    safe.rotate(rotation)

                    safe.processMatchCount += processor(previousValue, rotation, safe.value)
                }
            }

            return safe
        }
    }
}