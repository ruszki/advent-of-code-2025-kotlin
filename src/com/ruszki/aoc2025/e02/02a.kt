package com.ruszki.aoc2025.e02

fun main() {
    println("11-22 invalid count: ${Range(11u, 22u).invalidCount()}")
    println("95-115 invalid count: ${Range(99u, 115u).invalidCount()}")
    println("1188511880-1188511890 invalid count: ${Range(1188511880u, 1188511890u).invalidCount()}")
    println("2121212118-2121212124 invalid count: ${Range(2121212118u, 2121212124u).invalidCount()}")
    println("38593856-38593862 invalid count: ${Range(38593856u, 38593862u).invalidCount()}")
}
