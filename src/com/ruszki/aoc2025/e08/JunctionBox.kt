package com.ruszki.aoc2025.e08

data class JunctionBox(val position: Position) {
    private val connections = mutableSetOf<JunctionBox>()

    fun addConnection(other: JunctionBox) {
        if (!connections.contains(other)) {
            connections.add(other)

            other.addConnection(this)
        }
    }

    fun isConnectedTo(other: JunctionBox): Boolean {
        return connections.contains(other)
    }

    override fun toString(): String {
        return position.toString()
    }
}