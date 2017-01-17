package com.iagl.sma.wator

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import com.iagl.sma.core.pickRandomDirection
import com.iagl.sma.wator.Properties
import java.awt.Color


/**
 * Created by bacquet on 17/01/17.
 */
class Fish(x:Int, y:Int) : Agent(x,y,Color.BLUE) {
    private var tempX =0
    private var tempY =0
    private var direction = Direction.IDLE
    var breedTime : Int
    var alive : Boolean
    var moved : Boolean

    init {
        moved = false
        alive = true
        breedTime = Properties.instance.fishBreedTime
    }
    override fun decide(environnement: Array<Array<Agent?>>) {

        direction= pickRandomDirection()
        tempX = x+direction.x
        tempY = y+direction.y
        breedTime= (breedTime+1)%Properties.instance.fishBreedTime
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
/**
 * Created by Nathan on 17/01/2017.
 */
