package com.ruszki.aoc2025.e10

class LightBoardSetting(private val lights: MutableList<Boolean>) {
    fun getLights(): List<Boolean> = lights

    fun applyButton(button: Button) {
        button.switchedLights.forEach {
            lights[it.toInt()] = !lights[it.toInt()]
        }
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
            val lights = lightBoardString.map { '#' == it }.toMutableList()

            return LightBoardSetting(lights)
        }
    }
}