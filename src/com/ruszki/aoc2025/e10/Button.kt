package com.ruszki.aoc2025.e10

class Button(val switches: Set<ULong>) {
    override fun toString(): String {
        return switches.joinToString(",")
    }

    companion object {
        fun from(buttonString: String): Button {
            val switchedLights = buttonString.split(",").map { it.toULong() }.toSet()

            return Button(switchedLights)
        }
    }
}