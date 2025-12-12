package com.ruszki.aoc2025.e11

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Reactor(val deviceList: List<Device>) {
    override fun toString(): String {
        return deviceList.joinToString("\n") { it.toString() }
    }

    companion object {
        fun load(pathString: String): Reactor {
            val deviceMap = mutableMapOf<String, Device>()

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (line.isNotBlank()) {
                        Device.from(line, deviceMap)
                    }
                }
            }

            return Reactor(deviceMap.values.toList())
        }
    }
}