package com.ruszki.aoc2025.e04

class GridLine(previousGL: GridLine? = null) {
    var previous: GridLine? = previousGL
        private set

    var next: GridLine? = null
        private set

    private val rolls = mutableListOf<PaperRoll?>()

    fun addEmptySpace() {
        rolls.add(null)
    }

    fun addRoll(roll: PaperRoll) {
        rolls.add(roll)

        val rollIndex = rolls.size - 1

        NeighbourType.entries.forEach { type ->
            run {
                val neighbour = type.rollGetter(this, rollIndex)

                if (neighbour != null) {
                    roll.setNeighbour(type, neighbour)
                }
            }
        }
    }

    fun getRoll(rollIndex: Int): PaperRoll? {
        return rolls[rollIndex]
    }

    fun removePrevious() {
        val removedNeighbourTypes = listOf(NeighbourType.LEFT_UP, NeighbourType.UP, NeighbourType.RIGHT_UP)

        for (rollIndex in 0..<rolls.size) {
            for (removedNeighbourType in removedNeighbourTypes) {
                val roll = rolls[rollIndex]
                val neighbour = removedNeighbourType.rollGetter(this, rollIndex)

                if (roll != null && neighbour != null) {
                    roll.removeNeighbour(removedNeighbourType, neighbour)
                }
            }
        }

        previous = null
    }

    fun setNext(value: GridLine) {
        if (next == null) {
            next = value

            val addedNeighbourTypes = listOf(NeighbourType.LEFT_DOWN, NeighbourType.DOWN, NeighbourType.RIGHT_DOWN)

            for (rollIndex in 0..<rolls.size) {
                for (addedNeighbourType in addedNeighbourTypes) {
                    val roll = rolls[rollIndex]
                    val neighbour = addedNeighbourType.rollGetter(this, rollIndex)

                    if (roll != null && neighbour != null) {
                        roll.setNeighbour(addedNeighbourType, neighbour)
                    }
                }
            }
        }
    }

    fun size(): Int {
        return rolls.size
    }

    override fun toString(): String {
        return rolls.joinToString("") { if (it == null) "." else "@" }
    }
}
