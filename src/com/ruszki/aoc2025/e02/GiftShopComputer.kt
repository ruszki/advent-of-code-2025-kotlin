package com.ruszki.aoc2025.e02

import java.nio.file.Files
import java.nio.file.Paths

class GiftShopComputer {
    var invalidSum: ULong = 0uL
        private set

    companion object {
        fun loadSimple(path: String): GiftShopComputer {
            return load(path) { it.simpleInvalidSum() }
        }

        fun loadMultiple(path: String): GiftShopComputer {
            return load(path) { it.multipleInvalidSum() }
        }

        private fun load(pathString: String, processor: (Range) -> ULong): GiftShopComputer {
            val giftShopComputer = GiftShopComputer()
            var state = State.START
            var startString = ""
            var endString = ""

            val path = Paths.get(pathString)

            Files.newBufferedReader(path).use { reader ->
                var charInt = 0

                while (reader.read().also { charInt = it } != -1) {
                    val char = charInt.toChar()

                    if (char == '-') {
                        state = State.END
                    } else if (char == ',') {
                        state = State.START

                        val start = startString.toULong()
                        val end = endString.toULong()

                        startString = ""
                        endString = ""

                        val range = Range(start, end)

                        giftShopComputer.invalidSum += processor(range)
                    } else if (state == State.START) {
                        if (char.isDigit()) {
                            startString += char
                        }
                    } else {
                        if (char.isDigit()) {
                            endString += char
                        }
                    }
                }
            }

            val start = startString.toULong()
            val end = endString.toULong()

            val range = Range(start, end)

            giftShopComputer.invalidSum += processor(range)

            return giftShopComputer
        }
    }

    enum class State {
        START, END
    }
}