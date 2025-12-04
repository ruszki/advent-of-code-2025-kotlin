package com.ruszki.aoc2025.e02

fun main() {
    println("Is 55 invalid: ${Value(55u).isInvalid()}")
    println("Is 6464 invalid: ${Value(6464u).isInvalid()}")
    println("Is 6465 invalid: ${Value(6465u).isInvalid()}")
}