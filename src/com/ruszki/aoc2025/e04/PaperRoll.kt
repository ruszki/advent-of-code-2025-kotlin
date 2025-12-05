package com.ruszki.aoc2025.e04

class PaperRoll {
    private val neighbours = mutableMapOf<NeighbourType, PaperRoll>()

    fun getNeighbour(type: NeighbourType): PaperRoll? {
        return neighbours[type]
    }

    fun setNeighbour(type: NeighbourType, value: PaperRoll) {
        neighbours[type] = value

        value.neighbours[oppositeNeighbourTypes[type]!!] = this
    }

    fun removeNeighbour(type: NeighbourType, value: PaperRoll) {
        neighbours.remove(type)

        value.neighbours.remove(oppositeNeighbourTypes[type]!!)
    }

    fun remove() {
        for (neighbourEntry in neighbours.entries) {
            neighbourEntry.value.neighbours.remove(oppositeNeighbourTypes[neighbourEntry.key]!!)
        }

        neighbours.clear()
    }

    override fun toString(): String {
        val neighbourToString = {type: NeighbourType -> if (neighbours[type] == null) "." else "@"}

        return "${neighbourToString(NeighbourType.LEFT_UP)}${neighbourToString(NeighbourType.UP)}${
            neighbourToString(
                NeighbourType.RIGHT_UP
            )
        }\n${neighbourToString(NeighbourType.LEFT)}@${neighbourToString(NeighbourType.RIGHT)}\n${
            neighbourToString(
                NeighbourType.LEFT_DOWN
            )
        }${neighbourToString(NeighbourType.DOWN)}${
            neighbourToString(
                NeighbourType.RIGHT_DOWN
            )
        }"
    }

    fun isFree(): Boolean {
        return neighbours.size < 4
    }

    fun neighbourCount(): Int {
        return neighbours.size
    }

    companion object {
        private val oppositeNeighbourTypes = mapOf(
            NeighbourType.LEFT_UP to NeighbourType.RIGHT_DOWN,
            NeighbourType.UP to NeighbourType.DOWN,
            NeighbourType.RIGHT_UP to NeighbourType.LEFT_DOWN,
            NeighbourType.LEFT to NeighbourType.RIGHT,
            NeighbourType.RIGHT to NeighbourType.LEFT,
            NeighbourType.LEFT_DOWN to NeighbourType.RIGHT_UP,
            NeighbourType.DOWN to NeighbourType.UP,
            NeighbourType.RIGHT_DOWN to NeighbourType.LEFT_UP
            )
    }


}