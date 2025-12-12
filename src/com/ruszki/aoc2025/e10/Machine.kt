package com.ruszki.aoc2025.e10

import java.math.BigInteger
import java.util.Deque
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.concurrent.thread

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
        val initialMachineSettings = MachineSettings(initialJoltageSettings, buttons.sortedWith { a, b -> b.switches.size.compareTo(a.switches.size) }, 0uL)

        var currentSettings: Collection<MachineSettings> = listOf(initialMachineSettings)

        while (currentSettings.isNotEmpty()) {
            if (currentSettings.size < 10000) {
                val response = processCurrentSettings(currentSettings, fewestButtonPress)

                currentSettings = response.first
                fewestButtonPress = response.second
            } else {
                val responses = mutableListOf<MachineSettings>()

                val threads = currentSettings.chunked(currentSettings.size / 32).map {
                    thread {
                        val response = processDFCurrentSettings(it, fewestButtonPress)

                        synchronized(responses) {
                            responses.addAll(response.first)

                            if (fewestButtonPress > response.second) {
                                fewestButtonPress = response.second
                            }
                        }
                    }
                }

                threads.forEach { it.join() }

                currentSettings = responses
            }
        }

        println("$fewestButtonPress")

        return fewestButtonPress
    }

    private fun processDFCurrentSettings(incomingSettings: Iterable<MachineSettings>, previousFewestButtonPress: ULong): Pair<List<MachineSettings>, ULong> {
        var fewestButtonPress = previousFewestButtonPress

        val currentSettings = incomingSettings.toMutableList()

        while (currentSettings.isNotEmpty()) {
            val currentSetting = currentSettings.removeLast()

            val currentRemainingJoltageList =
                currentSetting.joltage.joltage.mapIndexed { index, j -> joltageRequirements.joltage[index] - j }
            val buttonCounts = currentSetting.joltage.joltage.mapIndexed { index, _ ->
                currentSetting.buttons.filter {
                    it.switches.contains(index.toULong())
                }.size.toULong()
            }
            var currentIndex = -1
            var minimumPossibility = BigInteger.ZERO

            for (i in currentRemainingJoltageList.indices) {
                val currentRemainingVoltage = currentRemainingJoltageList[i]
                val currentButtonCount = buttonCounts[i]

                if (currentRemainingVoltage != 0uL) {
                    val elements =
                        BigInteger.valueOf(currentRemainingVoltage.toLong() + currentButtonCount.toLong() - 1)
                    val combinations = BigInteger.valueOf(currentButtonCount.toLong() - 1)
                    val currentPossibility =
                        factorial(elements).divide((factorial(combinations).multiply(factorial(elements - combinations))))

                    if (currentIndex < 0 || minimumPossibility > currentPossibility) {
                        currentIndex = i
                        minimumPossibility = currentPossibility
                    }
                }
            }

            val currentButtons = currentSetting.buttons.filter { it.switches.contains(currentIndex.toULong()) }.toList()
            val remainingJoltage =
                joltageRequirements.joltage[currentIndex] - currentSetting.joltage.joltage[currentIndex]
            val newButtonPresses = currentSetting.buttonPresses + remainingJoltage

            if (currentButtons.isEmpty() || newButtonPresses > fewestButtonPress) {
                continue
            }

            val possibleButtonPressList = mutableListOf<List<Int>>()
            fillPossibleButtonPressList(
                remainingJoltage.toInt(),
                0,
                mutableListOf(),
                possibleButtonPressList,
                currentButtons.lastIndex
            )

            val newButtons = currentSetting.buttons.filter { !it.switches.contains(currentIndex.toULong()) }.toList()

            for (possibleButtonPress in possibleButtonPressList) {
                var newSetting = currentSetting.joltage

                possibleButtonPress.forEachIndexed { buttonIndex, buttonPressCount ->
                    val currentButton = currentButtons[buttonIndex]

                    if (buttonPressCount > 0) {
                        newSetting = newSetting.applyButton(currentButton, buttonPressCount.toULong())
                    }
                }

                if (newSetting == joltageRequirements) {
                    if (fewestButtonPress > newButtonPresses) {
                        fewestButtonPress = newButtonPresses
                    }
                } else if (newSetting < joltageRequirements && newButtons.isNotEmpty()) {
                    currentSettings.add(MachineSettings(newSetting, newButtons, newButtonPresses))
                }
            }
        }

        return Pair(emptyList(), fewestButtonPress)
    }

    private fun processCurrentSettings(currentSettings: Iterable<MachineSettings>, previousFewestButtonPress: ULong): Pair<List<MachineSettings>, ULong> {
        val returnSettings = mutableListOf<MachineSettings>()
        var fewestButtonPress = previousFewestButtonPress

        for (currentSetting in currentSettings) {
            val currentRemainingJoltageList =
                currentSetting.joltage.joltage.mapIndexed { index, j -> joltageRequirements.joltage[index] - j }
            val buttonCounts = currentSetting.joltage.joltage.mapIndexed { index, _ ->
                currentSetting.buttons.filter {
                    it.switches.contains(index.toULong())
                }.size.toULong()
            }
            var currentIndex = -1
            var minimumPossibility = BigInteger.ZERO

            for (i in currentRemainingJoltageList.indices) {
                val currentRemainingVoltage = currentRemainingJoltageList[i]
                val currentButtonCount = buttonCounts[i]

                if (currentRemainingVoltage != 0uL) {
                    val elements =
                        BigInteger.valueOf(currentRemainingVoltage.toLong() + currentButtonCount.toLong() - 1)
                    val combinations = BigInteger.valueOf(currentButtonCount.toLong() - 1)
                    val currentPossibility =
                        factorial(elements).divide((factorial(combinations).multiply(factorial(elements - combinations))))

                    if (currentIndex < 0 || minimumPossibility > currentPossibility) {
                        currentIndex = i
                        minimumPossibility = currentPossibility
                    }
                }
            }

            val currentButtons = currentSetting.buttons.filter { it.switches.contains(currentIndex.toULong()) }.toList()
            val remainingJoltage =
                joltageRequirements.joltage[currentIndex] - currentSetting.joltage.joltage[currentIndex]
            val newButtonPresses = currentSetting.buttonPresses + remainingJoltage

            if (currentButtons.isEmpty() || newButtonPresses > fewestButtonPress) {
                continue
            }

            val possibleButtonPressList = mutableListOf<List<Int>>()
            fillPossibleButtonPressList(
                remainingJoltage.toInt(),
                0,
                mutableListOf(),
                possibleButtonPressList,
                currentButtons.lastIndex
            )

            val newButtons = currentSetting.buttons.filter { !it.switches.contains(currentIndex.toULong()) }.toList()

            for (possibleButtonPress in possibleButtonPressList) {
                var newSetting = currentSetting.joltage

                possibleButtonPress.forEachIndexed { buttonIndex, buttonPressCount ->
                    val currentButton = currentButtons[buttonIndex]

                    if (buttonPressCount > 0) {
                        newSetting = newSetting.applyButton(currentButton, buttonPressCount.toULong())
                    }
                }

                if (newSetting == joltageRequirements) {
                    if (fewestButtonPress > newButtonPresses) {
                        fewestButtonPress = newButtonPresses
                    }
                } else if (newSetting < joltageRequirements && newButtons.isNotEmpty()) {
                    returnSettings.add(MachineSettings(newSetting, newButtons, newButtonPresses))
                }
            }
        }

        return Pair(returnSettings, fewestButtonPress)
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