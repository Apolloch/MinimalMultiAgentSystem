package com.iagl.sma

import org.omg.CORBA.Environment
import java.awt.Color


abstract class Agent(var x:Int,var y:Int,var color: Color){
    var direction : Direction

    init {
        direction = pickRandomDirection()
    }

    constructor(x:Int,y:Int,color: Color,direction: Direction):this(x,y,color){
        this.direction = direction
    }

    abstract fun decide(environnement: Array<Array<Agent?>>)

    abstract  fun borderGestion(environnement: Array<Array<Agent?>>)

    abstract  fun particleCollisionGestion(environnement: Array<Array<Agent?>>)

    override fun toString(): String {
        return "Agent,$color,$x,$y,$direction"
    }
}

