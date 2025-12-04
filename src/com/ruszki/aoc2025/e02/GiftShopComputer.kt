package com.ruszki.aoc2025.e02

import java.io.File
import java.io.FileReader

class GiftShopComputer {
    var invalidSum = 0uL

    fun load(path: String) {
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

                            invalidSum += Range(start, end).invalidSum()
                        } else if (state == State.START) {
                            if (char.isDigit()) {
                                startString += char
                            }
                        } else {
                            if (char.isDigit()) {
                                endString += char
                            }
                        }
                    } != -1) {}
            }

        val start = startString.toULong()
        val end = endString.toULong()

        invalidSum += Range(start, end).invalidSum()
    }

    enum class State {
        START, END
    }
}