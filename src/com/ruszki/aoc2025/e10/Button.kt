package com.ruszki.aoc2025.e10

class Button(val switchedLights: List<ULong>) {
    override fun toString(): String {
        return switchedLights.joinToString(",")
    }

    companion object {
        fun from(buttonString: String): Button {
            val switchedLights = buttonString.split(",").map { it.toULong() }.toList()

            return Button(switchedLights)
        }
    }
}