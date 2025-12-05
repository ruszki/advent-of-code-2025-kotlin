package com.ruszki.aoc2025.e03

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

data class BatteryRack(val outputJoltage: ULong) {
    companion object {
        fun from(pathString: String, usedBanks: UInt): BatteryRack {
            val path = Paths.get(pathString)

            val outputJoltage = Files.lines(path).use { lines ->
                lines.reduce(0uL, { outputJoltage, line ->
                    outputJoltage + if (line.isNotEmpty()) Battery.from(line).maximumJoltage(usedBanks) else 0uL
                }, {a, b -> a.plus(b)})
            }

            return BatteryRack(outputJoltage)
        }
    }
}