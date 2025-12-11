package com.ruszki.aoc2025.e10

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Factory(val machines: List<Machine>) {
    override fun toString(): String {
        return machines.joinToString("\n") { it.toString() }
    }

    companion object {
        fun load(pathString: String): Factory {
            val machineList = mutableListOf<Machine>()

            val path = Paths.get(pathString)

            Files.lines(path).use { lines ->
                lines.forEach { line ->
                    if (line.isNotBlank()) {
                        val machine = Machine.from(line)

                        machineList.add(machine)
                    }
                }
            }

            return Factory(machineList)
        }
    }
}