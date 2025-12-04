package com.ruszki.aoc2025.e03

fun main() {
    println("max joltage of 987654321111111: ${Battery.from("987654321111111").maximumJoltage()}")
    println("max joltage of 811111111111119: ${Battery.from("811111111111119").maximumJoltage()}")
    println("max joltage of 234234234234278: ${Battery.from("234234234234278").maximumJoltage()}")
    println("max joltage of 818181911112111: ${Battery.from("818181911112111").maximumJoltage()}")
}