package com.ruszki.aoc2025.e11

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Reactor(val deviceList: List<Device>) {
    fun getRouteCountFromYou(): ULong {
        val outputToDevice = deviceList.map { device -> device.getOutputs().map { output -> output to device } }.flatten().groupBy( { it.first  }, { it.second })

        val initialDevices = deviceList.filter { it.connectedToOutput }.toMutableSet()

        val remainingRoutes = mutableListOf<DeviceRoute>()
        var finishedRouteCount = 0uL

        for (device in initialDevices) {
            if (device.isYou) {
                finishedRouteCount += 1uL
            } else {
                val initialRoute = DeviceRoute(listOf(device))

                remainingRoutes.add(initialRoute)
            }
        }


        while (remainingRoutes.isNotEmpty()) {
            val currentRoute = remainingRoutes.removeLast()

            val inputs = outputToDevice[currentRoute.last()] ?: emptyList()

            for (input in inputs) {
                if (input.isYou) {
                    finishedRouteCount += 1uL
                } else if (!currentRoute.contains(input)) {
                    val newRoute = currentRoute.add(input)

                    remainingRoutes.add(newRoute)
                }
            }
        }

        return finishedRouteCount
    }

    private data class DeviceRoute(private val route: List<Device>) {
        fun last(): Device = route.last()
        fun contains(device: Device): Boolean = route.contains(device)

        fun add(device: Device): DeviceRoute {
            return DeviceRoute(route + device)
        }
    }

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