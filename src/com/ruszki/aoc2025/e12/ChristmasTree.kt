package com.ruszki.aoc2025.e12

data class ChristmasTree(val width: ULong, val height: ULong, val presents: Map<Present, ULong>) {
    override fun toString(): String {
        return "${width}x${height}: ${presents.entries.joinToString(" ") { it.value.toString() }}"
    }
}
