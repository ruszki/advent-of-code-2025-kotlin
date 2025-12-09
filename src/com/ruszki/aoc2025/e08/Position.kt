package com.ruszki.aoc2025.e08

import kotlin.math.pow
import kotlin.math.sqrt

data class Position(val x: ULong, val y: ULong, val z: ULong) {
    companion object {
        fun fromString(string: String): Position {
            val (x, y, z) = string.split(",").map { it.toULong() }

            return Position(x, y, z)
        }
    }

    fun distanceTo(other: Position): Double {
        return sqrt(
            (x.toDouble() - other.x.toDouble()).pow(2) +
                    (y.toDouble() - other.y.toDouble()).pow(2) +
                    (z.toDouble() - other.z.toDouble()).pow(2)
        )
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }
}
