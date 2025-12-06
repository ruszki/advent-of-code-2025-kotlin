package com.ruszki.aoc2025.e04

class GridLine(previousGL: GridLine? = null) {
    var previous: GridLine? = previousGL
        private set

    var next: GridLine? = null
        private set

    private val rolls = mutableListOf<GridLocationType>()

    fun addEmptySpace() {
        rolls.add(GridLocationType.NOTHING)
    }

    fun addRoll() {
        rolls.add(GridLocationType.PAPER_ROLL)
    }

    fun isRoll(rollIndex: Int): Boolean {
        return when (rolls.getOrNull(rollIndex)) {
            null -> false
            GridLocationType.PAPER_ROLL -> true
            GridLocationType.NOTHING -> false
        }
    }

    fun isFree(rollIndex: Int): Boolean {
        return if (rolls.getOrNull(rollIndex) == GridLocationType.PAPER_ROLL) {
            NeighbourType.entries.map { it.isRollGetter(this, rollIndex) }.count{ it } < 4
        } else {
            false
        }
    }

    fun getFreeRollCount(): ULong {
        return (0..<rolls.size).map { isFree(it) }.count{ it }.toULong()
    }

    fun removeFreeRolls(): ULong {
        val removedRolls = (0..<rolls.size).filter { isFree(it) }.toList()

        removedRolls.forEach { rolls[it] = GridLocationType.NOTHING }

        return removedRolls.size.toULong()
    }

    fun setNext(value: GridLine) {
        if (next == null) {
            next = value
            value.previous = this
        }
    }

    fun size(): Int {
        return rolls.size
    }

    override fun toString(): String {
        return (0..<rolls.size).joinToString("") {
            if (isFree(it)) "x"
            else if (rolls[it] == GridLocationType.NOTHING) "."
            else "@"
        }
    }
}
