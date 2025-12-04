package com.ruszki.aoc2025.e03

import java.io.File

data class BatteryRack(val batteries: List<Battery>) {
    companion object {
        fun from(path: String): BatteryRack {
            val batteries = File(path)
                .useLines {
                    it.map { line ->
                        if (line.length > 0) Battery.from(line) else null
                    }.toList().filterNotNull()
                }

            return BatteryRack(batteries)
        }
    }

    fun outputJoltage(usedBanks: UInt): ULong {
        return batteries.fold(0uL) { sumJoltage, battery -> sumJoltage + battery.maximumJoltage(usedBanks) }
    }
}