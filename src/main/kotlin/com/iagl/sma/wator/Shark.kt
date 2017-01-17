package com.iagl.sma.wator

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import com.iagl.sma.core.pickRandomDirection
import com.iagl.sma.wator.Properties
import java.awt.Color

/**
 * Created by bacquet on 17/01/17.
 */
class Shark(x:Int, y:Int) : Agent(x,y, Color.BLACK) {
    private var tempX =0
    private var tempY =0
    private var direction = Direction.IDLE
    var breedTime : Int
    var alive : Boolean
    var moved : Boolean
    var starveTime : Int
    init {
        alive = true
        moved = false
        breedTime = 0
        starveTime = 0
    }

    override fun decide(environnement: Array<Array<Agent?>>) {
        breedTime++
        starveTime++
        if(seekAndDestroy()){

        }
        else{
            tryToMove()
        }
        if(moved && breedTime%Properties.instance.sharkBreedTime==0 ){
            reproduce()
        }
        if(starveTime==Properties.instance.sharkStarveTime)
            alive=false
        moved = false

        direction=pickRandomDirection()
        tempX = x+direction.x
        tempY = y+direction.y
        breedTime= (breedTime+1)%Properties.instance.sharkBreedTime

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

    private fun seekAndDestroy(environnement: Array<Array<Agent?>>): Boolean {
        var tempX : Int
        var tempY : Int
        var haveEat = false
        for(dir in Direction.values()){
            tempX=x+dir.x
            tempY=y+dir.y
            if(!Properties.instance.torique && tempX>0 && tempX<environnement.size && tempY>0 && tempY<environnement[0].size) {
                if (environnement[tempX][tempY] != null && environnement[tempX][tempY] is Fish) {
                    eatHim(environnement[tempX][tempY])
                }
                break
            }
        }
        return haveEat

    }

    fun borderGestion(environnement:Array<Array<Agent?>>) {
        if (!Properties.instance.torique)
            direction=direction.reverse()
        else {

            if (tempX > environnement.size - 1 )
                tempX = 0
            else if(tempX<0)
                tempX = environnement.size - 1

            if (tempY > environnement[0].size - 1)
                tempY = 0
            else if(tempY<0)
                tempY = environnement[0].size - 1
            println("x=$x,y=$y,tempx = $tempX , tempy = $tempY")
            if (environnement[tempX][tempY] == null) {
                environnement[x][y] = null
                x = tempX
                y = tempY
                environnement[tempX][tempY] = this
            } else {
                particleCollisionGestion(environnement)
            }
        }
    }

    fun particleCollisionGestion(environnement: Array<Array<Agent?>>) {
    }

    override fun toString(): String {
        return "Agent,$color,$x,$y,$direction"
    }
}