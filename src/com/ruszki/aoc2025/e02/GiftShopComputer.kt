package com.ruszki.aoc2025.e02

import java.io.File
import java.io.FileReader

class GiftShopComputer {
    fun loadSimple(path: String): ULong {
        return load(path) { it.simpleInvalidSum() }
    }

    fun loadMultiple(path: String): ULong {
        return load(path) { it.multipleInvalidSum() }
    }

    private fun load(path: String, processor: (Range) -> ULong): ULong {
        var invalidSum = 0uL
        var state = State.START
        var startString = ""
        var endString = ""

        FileReader(File(path))
            .use { reader ->
                while (reader.read()
                        .also {
                            val char = it.toChar()

                            if (char == '-') {
                                state = State.END
                            } else if (char == ',') {
                                state = State.START

                                val start = startString.toULong()
                                val end = endString.toULong()

                                startString = ""
                                endString = ""

                                val range = Range(start, end)

                                invalidSum += processor(range)
                            } else if (state == State.START) {
                                if (char.isDigit()) {
                                    startString += char
                                }
                            } else {
                                if (char.isDigit()) {
                                    endString += char
                                }
                            }
                        } != -1
                ) {
                }
            }

        val start = startString.toULong()
        val end = endString.toULong()

        val range = Range(start, end)

        return invalidSum + processor(range)
    }

    enum class State {
        START, END
    }
}