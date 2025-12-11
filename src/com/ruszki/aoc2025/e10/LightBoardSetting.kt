package com.ruszki.aoc2025.e10

class LightBoardSetting(val lights: List<Boolean>) {
    fun applyButton(button: Button): LightBoardSetting {
        val newLights =
            lights.mapIndexed { index, light -> if (button.switches.contains(index.toULong())) !light else light }
                .toList()

        return LightBoardSetting(newLights)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LightBoardSetting) return false

        return lights == other.lights
    }

    override fun hashCode(): Int {
        return lights.hashCode()
    }

    override fun toString(): String {
        return lights.joinToString("") { if (it) "#" else "." }
    }

    companion object {
        fun from(lightBoardString: String): LightBoardSetting {
            val lights = lightBoardString.map { '#' == it }.toList()

            return LightBoardSetting(lights)
        }
    }
}