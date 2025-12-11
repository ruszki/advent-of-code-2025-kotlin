package com.ruszki.aoc2025.e10

class JoltageSettings(val joltage: List<ULong>): Comparable<JoltageSettings> {
    fun applyButton(button: Button, times: ULong): JoltageSettings {
        val newJoltage = joltage.mapIndexed { index, j -> if (button.switches.contains(index.toULong())) j + times else j }

        return JoltageSettings(newJoltage)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JoltageSettings) return false

        return joltage == other.joltage
    }

    override fun hashCode(): Int {
        return joltage.hashCode()
    }

    override fun toString(): String {
        return joltage.joinToString(",")
    }

    override fun compareTo(other: JoltageSettings): Int {
        var result = 0

        for (index in joltage.indices) {
            if (joltage[index] > other.joltage[index]) {
                return 1
            } else if (joltage[index] < other.joltage[index]) {
                result = -1
            }
        }

        return result
    }

    companion object {
        fun from(joltageString: String): JoltageSettings {
            val joltage = joltageString.split(",").map { it.toULong() }.toList()

            return JoltageSettings(joltage)
        }
    }
}