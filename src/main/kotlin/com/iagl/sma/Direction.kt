package com.iagl.sma

import java.security.SecureRandom
import java.util.*

/**
 * Created by bacquet on 10/01/17.
 */

enum class Direction() {
    TOP,RIGHT,LEFT,BOTTOM,TOP_RIGHT,TOP_LEFT,BOTTOM_RIGHT,BOTTOM_LEFT
}

fun pickRandomDirection():Direction{
when(SecureRandom().nextInt(9)){
    0 -> return Direction.TOP
    1 -> return Direction.RIGHT
    2 -> return Direction.LEFT
    3 -> return Direction.BOTTOM
    4 -> return Direction.TOP_RIGHT
    5 -> return Direction.TOP_LEFT
    6 -> return Direction.BOTTOM_RIGHT
    else -> return Direction.BOTTOM_LEFT
}
}

fun main(args: Array<String>) {
    for (i in 0..7)
        println(pickRandomDirection())
}

