package com.ruszki.aoc2025.e04

enum class NeighbourType(val rollGetter: (GridLine, Int) -> PaperRoll?) {
    LEFT_UP({gl, index ->
        val previousGl = gl.previous

        if (previousGl == null) {
            null
        } else if (index == 0) {
            null
        } else {
            previousGl.getRoll(index - 1)
        }
    }),
    UP({gl, index ->
        gl.previous?.getRoll(index)
    }),
    RIGHT_UP({gl, index ->
        val previousGl = gl.previous

        if (previousGl == null) {
            null
        } else if (previousGl.size() >= index + 1) {
            null
        } else {
            previousGl.getRoll(index + 1)
        }
    }),
    LEFT({gl, index ->
        if (index == 0) {
            null
        } else {
            gl.getRoll(index - 1)
        }
    }),
    RIGHT({gl, index ->
        if (gl.size() == index + 1) {
            null
        } else {
            gl.getRoll(index + 1)
        }
    }),
    LEFT_DOWN({gl, index ->
        val nextGl = gl.next

        if (nextGl == null) {
            null
        } else if (index == 0) {
            null
        } else {
            nextGl.getRoll(index - 1)
        }
    }),
    DOWN({gl, index ->
        gl.next?.getRoll(index)
    }),
    RIGHT_DOWN({gl, index ->
        val nextGl = gl.next

        if (nextGl == null) {
            null
        } else if (nextGl.size() >= index + 1) {
            null
        } else {
            nextGl.getRoll(index + 1)
        }
    })
}