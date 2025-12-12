package com.ruszki.aoc2025.e11

class Device(private val name: String) {
    var reactorRouteCount: ULong? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }

    private val output = mutableSetOf<Device>()

    fun addOutput(device: Device) {
        output.add(device)
    }

    fun hasOutput(device: Device): Boolean {
        return output.contains(device)
    }

    override fun toString(): String {
        return "${name}: ${if (reactorRouteCount == 1uL) "out${if (output.isNotEmpty()) " " else ""}" else ""}${
            output.joinToString(
                " "
            ) { it.name }
        }"
    }

    companion object {
        fun from(line: String, devices: MutableMap<String, Device>) {
            val parts = line.split(": ")

            val name = parts[0]

            val device = devices.computeIfAbsent(name, { Device(name)})

            val deviceNames = parts[1].split(" ")

            for (deviceName in deviceNames) {
                if (deviceName == "out") {
                    device.reactorRouteCount = 1uL
                } else {
                    device.addOutput(devices.computeIfAbsent(deviceName) { Device(it) })
                }
            }
        }
    }
}