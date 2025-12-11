package com.ruszki.aoc2025.e10

import java.math.BigInteger

class Machine(
    val requiredLightBoardSetting: LightBoardSetting,
    val buttons: List<Button>,
    val joltageRequirements: JoltageSettings
) {
    fun getRequiredButtonPressesForOnline(): ULong {
        var fewestButtonPressCounter = 0uL
        val initialLightBoardSetting = LightBoardSetting(List(requiredLightBoardSetting.lights.size) { false })

        var currentSettings = listOf(initialLightBoardSetting)
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

    fun getRequiredButtonPressesForJoltage(): ULong {
        print("$this: ")
        var fewestButtonPress = ULong.MAX_VALUE
        val initialJoltageSettings = JoltageSettings(List(joltageRequirements.joltage.size) { 0uL })
        val initialMachineSettings = MachineSettings(initialJoltageSettings, buttons, 0uL)

        var currentSettings = listOf(initialMachineSettings)
        var nextSettings = mutableListOf<MachineSettings>()

        while (currentSettings.isNotEmpty()) {
            for (currentSetting in currentSettings) {
                val currentRemainingJoltageList = currentSetting.joltage.joltage.mapIndexed { index, j -> joltageRequirements.joltage[index] - j }
                val buttonCounts = currentSetting.joltage.joltage.mapIndexed { index, _ -> currentSetting.buttons.filter { it.switches.contains(index.toULong()) }.size.toULong() }
                var currentIndex = -1
                var minimumPossibility = BigInteger.ZERO

                for (i in currentRemainingJoltageList.indices) {
                    val currentRemainingVoltage = currentRemainingJoltageList[i]
                    val currentButtonCount = buttonCounts[i]

                    if (currentRemainingVoltage != 0uL) {
                        val elements = BigInteger.valueOf(currentRemainingVoltage.toLong() + currentButtonCount.toLong() - 1)
                        val combinations = BigInteger.valueOf(currentButtonCount.toLong() - 1)
                        val currentPossibility = factorial(elements).divide((factorial(combinations).multiply(factorial(elements - combinations))))

                        if (currentIndex < 0 || minimumPossibility > currentPossibility) {
                            currentIndex = i
                            minimumPossibility = currentPossibility
                        }
                    }
                }

                if (currentIndex < 0) {
                    if (fewestButtonPress > currentSetting.buttonPresses) {
                        fewestButtonPress = currentSetting.buttonPresses
                    }

                    continue
                }

                val currentButtons = currentSetting.buttons.filter { it.switches.contains(currentIndex.toULong()) }.toList()
                val remainingJoltage = joltageRequirements.joltage[currentIndex] - currentSetting.joltage.joltage[currentIndex]
                val newButtonPresses = currentSetting.buttonPresses + remainingJoltage

                if (currentButtons.isEmpty() || newButtonPresses > fewestButtonPress) {
                    continue
                }

                val possibleButtonPressList = mutableListOf<List<Int>>()
                fillPossibleButtonPressList(remainingJoltage.toInt(), 0, mutableListOf(), possibleButtonPressList, currentButtons.lastIndex)

                for (possibleButtonPress in possibleButtonPressList) {
                    var newSetting = currentSetting.joltage

                    possibleButtonPress.forEachIndexed { buttonIndex, buttonPressCount ->
                        val currentButton = currentButtons[buttonIndex]

                        repeat(buttonPressCount) {
                            newSetting = newSetting.applyButton(currentButton)
                        }
                    }

                    if (newSetting > joltageRequirements) {
                        continue
                    } else {
                        val newButtons = currentSetting.buttons.filter { !it.switches.contains(currentIndex.toULong()) }.toList()

                        nextSettings.add(MachineSettings(newSetting, newButtons, newButtonPresses))
                    }
                }
            }

            currentSettings = nextSettings
            nextSettings = mutableListOf()
        }

        println(fewestButtonPress)

        return fewestButtonPress
    }

    private data class MachineSettings(val joltage: JoltageSettings, val buttons: List<Button>, val buttonPresses: ULong)

    override fun toString(): String {
        return "[$requiredLightBoardSetting] ${buttons.joinToString(" ") { "($it)" }} {$joltageRequirements}"
    }

    companion object {
        fun from(machineString: String): Machine {
            val machinePartStrings = machineString.split(" ")

            var requiredLightBoardSetting: LightBoardSetting? = null
            val buttons = mutableListOf<Button>()
            var joltageRequirements: JoltageSettings? = null

            for (machinePart in machinePartStrings) {
                if (machinePart[0] == '[') {
                    val lightsString = machinePart.substring(1, machinePart.length - 1)

                    requiredLightBoardSetting = LightBoardSetting.from(lightsString)
                } else if (machinePart[0] == '(') {
                    val buttonString = machinePart.substring(1, machinePart.length - 1)

                    buttons.add(Button.from(buttonString))
                } else if (machinePart[0] == '{') {
                    val joltageString = machinePart.substring(1, machinePart.length - 1)

                    joltageRequirements = JoltageSettings.from(joltageString)
                }
            }

            require(buttons.isNotEmpty())
            require(requiredLightBoardSetting != null)
            require(joltageRequirements != null)
            require(joltageRequirements.joltage.size == requiredLightBoardSetting.lights.size)
            require(buttons.none { it.switches.max().toInt() >= requiredLightBoardSetting.lights.size })

            return Machine(requiredLightBoardSetting, buttons, joltageRequirements)
        }

        private fun fillPossibleButtonPressList(
            remaining: Int,
            binIndex: Int,
            current: MutableList<Int>,
            possibleButtonPressList: MutableList<List<Int>>,
            maxIndex: Int
        ) {
            if (binIndex == maxIndex) {
                current.add(remaining)
                possibleButtonPressList.add(current.toList())
                current.removeAt(current.lastIndex)
                return
            }

            for (count in 0..remaining) {
                current.add(count)
                fillPossibleButtonPressList(remaining - count, binIndex + 1, current, possibleButtonPressList, maxIndex)
                current.removeAt(current.lastIndex)
            }
        }

        private tailrec fun factorial(n: BigInteger, accumulator: BigInteger = BigInteger.valueOf(1)): BigInteger {
            return if (n <= BigInteger.valueOf(1)) {
                accumulator
            } else {
                factorial(n.minus(BigInteger.valueOf(1)), n.multiply(accumulator))
            }
        }
    }
}