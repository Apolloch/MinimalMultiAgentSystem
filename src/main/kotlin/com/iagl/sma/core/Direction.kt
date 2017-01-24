package com.iagl.sma.core

import java.security.SecureRandom
import java.util.*

/**
 * Created by bacquet on 10/01/17.
 */

enum class Direction(x:Int,y:Int) {
    TOP(0,-1),RIGHT(1,0),LEFT(-1,0),BOTTOM(0,1),TOP_RIGHT(1,-1),TOP_LEFT(-1,-1),BOTTOM_RIGHT(1,1),BOTTOM_LEFT(-1,1),IDLE(0,0);
    var x:Int
    var y:Int
    init {
        this.x=x
        this.y=y
    }

    fun reverse() : Direction {
        when(this){
            BOTTOM -> return TOP
            LEFT -> return RIGHT
            RIGHT -> return LEFT
            TOP -> return BOTTOM
            BOTTOM_LEFT -> return TOP_RIGHT
            BOTTOM_RIGHT -> return TOP_LEFT
            TOP_LEFT -> return BOTTOM_RIGHT
            TOP_RIGHT -> return BOTTOM_LEFT
            else -> return IDLE
        }
    }
}



fun pickRandomDirection(): Direction {
    when(SecureRandom().nextInt(9)){
        0 -> return Direction.TOP
        1 -> return Direction.RIGHT
        2 -> return Direction.LEFT
        3 -> return Direction.BOTTOM
        4 -> return Direction.TOP_RIGHT
        5 -> return Direction.TOP_LEFT
        6 -> return Direction.BOTTOM_RIGHT
        7 -> return Direction.BOTTOM_LEFT
        else -> return Direction.IDLE
    }
}
fun vonNeumanNeighbourhood():Array<Direction>{
    return arrayOf(Direction.TOP,Direction.BOTTOM,Direction.LEFT,Direction.RIGHT)
}
fun main(args: Array<String>) {
    for (i in 0..7)
        println(pickRandomDirection())
}

