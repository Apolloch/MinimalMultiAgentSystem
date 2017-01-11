package com.iagl.sma

import org.omg.CORBA.Environment
import java.awt.Color


open class Agent(var x:Int,var y:Int,var color: Color){
    var direction : Direction

    init {
        direction = pickRandomDirection()
    }

    constructor(x:Int,y:Int,color: Color,direction: Direction):this(x,y,color){
        this.direction = direction
    }

    fun decide(environnement: Array<Array<Agent?>>) {
        var tempX : Int
        var tempY : Int

        tempX = x+direction.x
        tempY = y+direction.y

        if(tempX >= 0 && tempX<environnement.size && tempY >= 0 && tempY<environnement[0].size){
            if(environnement[tempX][tempY] == null){
                environnement[x][y]=null
                x = tempX
                y=tempY
                environnement[tempX][tempY]=this
            }
            else{
                particleCollisionGestion(environnement)
            }
        }
        else{
            borderGestion(environnement)
        }
    }

    private fun borderGestion(environnement: Array<Array<Agent?>>) {
        direction=direction.reverse()
    }

    private fun particleCollisionGestion(environnement: Array<Array<Agent?>>) {
        environnement[x+direction.x][y+direction.y]?.direction=direction
        direction = direction.reverse()
        //println(this)

    }

    override fun toString(): String {
        return "Agent,$color,$x,$y,$direction"
    }
}


