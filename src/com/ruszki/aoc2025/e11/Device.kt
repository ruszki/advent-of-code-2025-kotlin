package com.ruszki.aoc2025.e11

class Device(private val name: String) {
    val isYou = name == "you"
    val isOut = name == "out"

    private val outputs = mutableSetOf<Device>()

    fun addOutput(device: Device) {
        outputs.add(device)
    }

    fun getOutputs(): Set<Device> {
        return outputs
    }

    override fun toString(): String {
        return "${name}: ${outputs.joinToString(" ") { it.name }}"
    }

    companion object {
        fun from(line: String, devices: MutableMap<String, Device>) {
            val parts = line.split(": ")

            val name = parts[0]

            val device = devices.computeIfAbsent(name, { Device(name)})

            val deviceNames = parts[1].split(" ")

            for (deviceName in deviceNames) {device.addOutput(devices.computeIfAbsent(deviceName) { Device(it) })
            }
        }
    }
}