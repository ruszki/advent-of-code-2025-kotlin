package com.ruszki.aoc2025.e11

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.use

class Reactor(val deviceList: List<Device>) {
    fun getRouteCountFromYou(): ULong {
        val youDevice = deviceList.first { it.isYou }

        return getRouteCountFromYou(youDevice, emptySet())
    }

    private fun getRouteCountFromYou(currentDevice: Device, visitedDevices: Set<Device>): ULong {
        if (currentDevice.isOut) {
            return 1uL
        } else {
            val newVisitedDevices = visitedDevices.plus(currentDevice)

            val routeCount = currentDevice.getOutputs().filter { it !in visitedDevices }
                .sumOf { getRouteCountFromYou(it, newVisitedDevices) }

            return routeCount
        }
    }

    fun getRouteCountFromSvrWithDacAndFft(): ULong {
        val svrDevice = deviceList.first { it.isSvr }
        val outDevice = deviceList.first { it.isOut }
        val dacDevice = deviceList.first { it.isDac }
        val fftDevice = deviceList.first { it.isFft }

        val svrToDacCount = getRouteCount(deviceList.filter { it != fftDevice }.toSet(), svrDevice, dacDevice)
        val dacToFftCount = getRouteCount(deviceList.filter { it != svrDevice }.toSet(), dacDevice, fftDevice)
        val fftToOutCount = getRouteCount(deviceList.filter { it != svrDevice && it != dacDevice }.toSet(), fftDevice, outDevice)

        val svrToFftCount = getRouteCount(deviceList.filter { it != dacDevice }.toSet(), svrDevice, fftDevice)
        val fftToDacCount = getRouteCount(deviceList.filter { it != svrDevice }.toSet(), fftDevice, dacDevice)
        val dacToOutCount = getRouteCount(deviceList.filter { it != svrDevice && it != fftDevice }.toSet(), dacDevice, outDevice)

        return svrToDacCount * dacToFftCount * fftToOutCount + svrToFftCount * fftToDacCount * dacToOutCount
    }

    private fun getRouteCount(devices: Set<Device>, start: Device, end: Device): ULong {
        val distanceMap = mutableMapOf<Device, ULong>()

        fun calculate(device: Device): ULong {
            return distanceMap.getOrPut(device) {
                if (device == end) {
                    1uL
                } else {
                    device.getOutputs().filter { devices.contains(it) }.sumOf { calculate(it) }
                }
            }
        }

        return calculate(start)
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