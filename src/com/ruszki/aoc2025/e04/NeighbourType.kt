package com.ruszki.aoc2025.e04

enum class NeighbourType(val rollGetter: (GridLine, Int) -> PaperRoll?) {
    LEFT_UP({ gl, index ->
        gl.previous?.getRoll(index - 1)
    }),
    UP({ gl, index ->
        gl.previous?.getRoll(index)
    }),
    RIGHT_UP({ gl, index ->
        gl.previous?.getRoll(index + 1)
    }),
    LEFT({ gl, index ->
        gl.getRoll(index - 1)
    }),
    RIGHT({ gl, index ->
        gl.getRoll(index + 1)
    }),
    LEFT_DOWN({ gl, index ->
        gl.next?.getRoll(index - 1)
    }),
    DOWN({ gl, index ->
        gl.next?.getRoll(index)
    }),
    RIGHT_DOWN({ gl, index ->
        gl.next?.getRoll(index + 1)
    })
}