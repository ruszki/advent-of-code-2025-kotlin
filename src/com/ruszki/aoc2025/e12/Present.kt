package com.ruszki.aoc2025.e12

data class Present(val id: ULong, val points: List<Pair<Long, Long>>) {
    override fun toString(): String {
        val maxX = points.maxOf { it.first }
        val maxY = points.maxOf { it.second }

        val pointsMatrix = (0..maxX).map { (0..maxY).map { false }.toMutableList() }

        points.forEach { pointsMatrix[it.first.toInt()][it.second.toInt()] = true }

        return "$id:\n${pointsMatrix.joinToString("\n") { row -> row.joinToString("") { item -> if (item) "#" else "." } }}"
    }
}
