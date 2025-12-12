package com.ruszki.aoc2025.e11

class Reactor(val deviceList: List<Device>) {
    override fun toString(): String {
        return deviceList.joinToString("\n") { it.toString() }
    }
}