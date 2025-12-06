package com.ruszki.aoc2025.e04

enum class NeighbourType(val isRollGetter: (GridLine, Int) -> Boolean) {
    LEFT_UP({ gl, index ->
        gl.previous?.isRoll(index - 1) ?: false
    }),
    UP({ gl, index ->
        gl.previous?.isRoll(index) ?: false
    }),
    RIGHT_UP({ gl, index ->
        gl.previous?.isRoll(index + 1) ?: false
    }),
    LEFT({ gl, index ->
        gl.isRoll(index - 1)
    }),
    RIGHT({ gl, index ->
        gl.isRoll(index + 1)
    }),
    LEFT_DOWN({ gl, index ->
        gl.next?.isRoll(index - 1) ?: false
    }),
    DOWN({ gl, index ->
        gl.next?.isRoll(index) ?: false
    }),
    RIGHT_DOWN({ gl, index ->
        gl.next?.isRoll(index + 1) ?: false
    })
}