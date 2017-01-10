package com.iagl.sma

import java.awt.Color


open class Agent(var x:Int,var y:Int,var color: Color){
    var direction : Direction
    init {
        direction = pickRandomDirection()
    }
    fun decide(environnement: Array<Array<Agent?>>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


