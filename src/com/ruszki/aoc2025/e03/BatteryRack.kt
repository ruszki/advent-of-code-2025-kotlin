package com.ruszki.aoc2025.e03

import java.io.File

class BatteryRack(val batteries: List<Battery>) {
    companion object {
        fun from(path: String): BatteryRack {
            val batteries = File(path)
                .useLines {
                    it.map { line ->
                        Battery.from(line)
                    }
                }.toList()

            return BatteryRack(batteries)
        }
    }

    fun outputJoltage(): ULong {
        return batteries.fold(0uL) { sumJoltage, battery -> sumJoltage + battery.maximumJoltage() }
    }
}