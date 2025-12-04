package com.ruszki.aoc2025.e01

data class Rotation(val direction: Direction, val distance: UInt) {
    companion object {
        fun from(s: String): Rotation {
            val direction: Direction = when (s[0]) {
                'R' -> Direction.RIGHT
                'L' -> Direction.LEFT
                else -> throw Exception("Invalid direction")
            }

            val distance = s.subSequence(1, s.length).toString().toUInt()

            return Rotation(direction, distance)
        }
    }
}
