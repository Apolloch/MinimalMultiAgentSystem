package com.iagl.sma

import java.awt.Color

/**
 * Created by bacquet on 17/01/17.
 */
class Particule(x:Int,y:Int,color: Color,direction: Direction) : Agent(x,y,color,direction) {
    private var tempX =0
    private var tempY =0
    override fun decide(environnement: Array<Array<Agent?>>) {


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

    override fun borderGestion(environnement: Array<Array<Agent?>>) {
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

    override fun particleCollisionGestion(environnement: Array<Array<Agent?>>) {
        environnement[tempX][tempY]?.direction=direction
        direction = direction.reverse()
        //println(this)

    }

    override fun toString(): String {
        return "Agent,$color,$x,$y,$direction"
    }
}