package com.ruszki.aoc2025.e01

import java.nio.file.Files
import java.nio.file.Paths

class Safe {
    var value: UInt = 50u
        private set

    private fun rotate(rotation: Rotation) {
        value = when (rotation.direction) {
            Direction.RIGHT -> (value + rotation.distance).mod(100u)
            Direction.LEFT -> (value.toInt() - rotation.distance.toInt()).mod(100).toUInt()
        }
    }

    fun open(pathString: String, processor: (UInt, Rotation, UInt) -> Unit) {
        val path = Paths.get(pathString)

        Files.lines(path).use { lines ->
            lines.forEach { line ->
                run {
                    val previousValue = value
                    val rotation = Rotation.from(line)

                    rotate(rotation)

                    processor(previousValue, rotation, value)
                }
            }
        }
    }
}