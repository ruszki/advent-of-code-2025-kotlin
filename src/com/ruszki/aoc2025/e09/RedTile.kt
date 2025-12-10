package com.ruszki.aoc2025.e09

class RedTile(val x: ULong, val y: ULong) {
    var previous: RedTile = this
    var next: RedTile = this

    fun areaOfSpannedRectangle(other: RedTile): ULong {
        return (ULongUtils.difference(x, other.x) + 1uL) * (ULongUtils.difference(y, other.y) + 1uL)
    }

    override fun toString(): String {
        return "($x, $y)"
    }


    companion object {
        fun from(s: String): RedTile {
            val (x, y) = s.split(',').map(String::toULong)

            return RedTile(x, y)
        }
    }
}
