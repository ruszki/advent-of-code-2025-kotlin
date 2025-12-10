package com.ruszki.aoc2025.e09

import kotlin.math.abs

data class RedTile(val x: ULong, val y: ULong) {
    fun areaOfSpannedRectangle(other: RedTile): ULong {
        return (uLongAbs(x, other.x) + 1uL) * (uLongAbs(y, other.y) + 1uL)
    }

    private fun uLongAbs(a : ULong, b :ULong): ULong {
        if (a > b) {
            return a - b
        } else {
            return b - a
        }
    }

    companion object {
        fun from(s: String): RedTile {
            val (x, y) = s.split(',').map(String::toULong)

            return RedTile(x, y)
        }
    }
}
