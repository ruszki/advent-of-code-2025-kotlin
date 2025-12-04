package com.ruszki.aoc2025.e02

fun main() {
    val giftShopComputer = GiftShopComputer()

    giftShopComputer.load("src/input/02.txt")

    println("invalid sum: ${giftShopComputer.invalidSum}")
}
