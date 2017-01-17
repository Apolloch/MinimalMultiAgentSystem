package com.iagl.sma.core

import com.iagl.sma.core.Direction
import com.iagl.sma.core.pickRandomDirection
import org.omg.CORBA.Environment
import java.awt.Color


abstract class Agent(var x:Int,var y:Int,var color: Color){


    abstract fun decide(environnement: Array<Array<Agent?>>)


    override fun toString(): String {
        return "Agent,$color,$x,$y"
    }
}

