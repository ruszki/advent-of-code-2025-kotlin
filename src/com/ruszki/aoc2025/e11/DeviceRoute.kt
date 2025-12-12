package com.ruszki.aoc2025.e11

class DeviceRoute(private val devices: List<Device>) {
    fun contains(device: Device) = device in devices

    fun plus(device: Device): DeviceRoute {
        return DeviceRoute(devices + device)
    }

    override fun toString(): String {
        return devices.reversed().joinToString("->") { it.name }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DeviceRoute) return false

        return devices == other.devices
    }

    override fun hashCode(): Int {
        return devices.hashCode()
    }
}