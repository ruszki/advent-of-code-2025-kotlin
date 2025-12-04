package com.ruszki.aoc2025.e01

import java.io.File
import kotlin.sequences.forEach

class Safe {
    var value: UInt = 50u
        private set

    private fun rotate(rotation: Rotation) {
        value = when (rotation.direction) {
            Direction.RIGHT -> (value + rotation.distance).mod(100u)
            Direction.LEFT -> (value.toInt() - rotation.distance.toInt()).mod(100).toUInt()
        }
    }

    fun open(path: String, processor: (UInt) -> Unit) {
        File(path)
            .useLines { it.forEach { line ->
                run {
                    val rotation = Rotation.from(line)

                    rotate(rotation)

                    processor(value)
                }
            } }
    }
}