package com.ruszki.aoc2025.e08

data class Position(val x: ULong, val y: ULong, val z: ULong) {
    companion object {
        fun fromString(string: String): Position {
            val (x, y, z) = string.split(",").map { it.toULong() }

            return Position(x, y, z)
        }
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }


}
