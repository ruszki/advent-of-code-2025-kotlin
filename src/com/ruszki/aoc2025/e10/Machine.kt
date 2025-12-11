package com.ruszki.aoc2025.e10

class Machine(
    val requiredLightBoardSetting: LightBoardSetting,
    val buttons: List<Button>,
    val joltageRequirements: List<ULong>
) {
    val currentLightBoardSetting = LightBoardSetting(MutableList(requiredLightBoardSetting.lights.size) { false })

    fun getFewestButtonPress(): ULong {
        var fewestButtonPressCounter = 0uL
        val initialLightBoardSetting = LightBoardSetting(MutableList(requiredLightBoardSetting.lights.size) { false })

        var currentSettings = mutableListOf(initialLightBoardSetting)
        var nextSettings = mutableListOf<LightBoardSetting>()

        while (true) {
            fewestButtonPressCounter += 1uL

            for (currentSetting in currentSettings) {
                for (button in buttons) {
                    val newSetting = currentSetting.applyButton(button)

                    if (newSetting == requiredLightBoardSetting) {
                        return fewestButtonPressCounter
                    } else {
                        nextSettings.add(newSetting)
                    }
                }
            }

            currentSettings = nextSettings
            nextSettings = mutableListOf()
        }
    }

    override fun toString(): String {
        return "[$currentLightBoardSetting]->[$requiredLightBoardSetting] ${buttons.joinToString(" ") { "($it)" }} {${
            joltageRequirements.joinToString(
                ","
            )
        }}"
    }

    companion object {
        fun from(machineString: String): Machine {
            val machinePartStrings = machineString.split(" ")

            var requiredLightBoardSetting: LightBoardSetting? = null
            val buttons = mutableListOf<Button>()
            var joltageRequirements: List<ULong>? = null

            for (machinePart in machinePartStrings) {
                if (machinePart[0] == '[') {
                    val lightsString = machinePart.substring(1, machinePart.length - 1)

                    requiredLightBoardSetting = LightBoardSetting.from(lightsString)
                } else if (machinePart[0] == '(') {
                    val buttonString = machinePart.substring(1, machinePart.length - 1)

                    buttons.add(Button.from(buttonString))
                } else if (machinePart[0] == '{') {
                    val joltageString = machinePart.substring(1, machinePart.length - 1)

                    joltageRequirements = joltageString.split(",").map { it.toULong() }.toList()
                }
            }

            require(buttons.isNotEmpty())
            require(requiredLightBoardSetting != null)
            require(joltageRequirements != null)
            require(joltageRequirements.size == requiredLightBoardSetting.lights.size)
            require(buttons.none { it.switchedLights.max().toInt() >= requiredLightBoardSetting.lights.size })

            return Machine(requiredLightBoardSetting, buttons, joltageRequirements)
        }
    }
}