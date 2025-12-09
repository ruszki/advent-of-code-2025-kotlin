package com.ruszki.aoc2025.e08

data class JunctionBox(val position: Position) {
    override fun toString(): String {
        return position.toString()
    }
}