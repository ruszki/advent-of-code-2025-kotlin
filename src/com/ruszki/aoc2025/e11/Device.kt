package com.ruszki.aoc2025.e11

class Device(private val name: String) {
    var connectedToOutput: Boolean = false
        private set

    val isYou = name == "you"

    private val outputs = mutableSetOf<Device>()

    fun addOutput(device: Device) {
        outputs.add(device)
    }

    fun getOutputs(): Set<Device> {
        return outputs
    }

    override fun toString(): String {
        return "${name}: ${if (connectedToOutput) "out${if (outputs.isNotEmpty()) " " else ""}" else ""}${
            outputs.joinToString(
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
                    device.connectedToOutput = true
                } else {
                    device.addOutput(devices.computeIfAbsent(deviceName) { Device(it) })
                }
            }
        }
    }
}