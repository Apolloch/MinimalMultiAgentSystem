package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import java.awt.Color

/**
 * Created by Apolloch on 20/01/2017.
 */

class Avatar(x :Int , y : Int):Agent(x,y, Color.BLUE) {
    var direction: Direction
    private var  tempY: Int
    private var tempX:Int

    init {
        tempX = x
        tempY = y
        direction = Direction.IDLE
    }

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
                particleCollisionGestion(environnement as Array<Array<Agent?>>)
            }
        }
        else{
            borderGestion(environnement as Array<Array<Agent?>>)
        }
    }

    fun borderGestion(environnement: Array<Array<Agent?>>) {
        if (!PropertiesAvatar.INSTANCE.torique)
            direction=Direction.IDLE
        if(PropertiesAvatar.INSTANCE.torique ) {
            if (tempX < 0) {
                tempX = environnement.size + direction.x
            } else {
                tempX = tempX % environnement.size;
            }
            if (tempY < 0) {
                tempY = environnement.size + direction.y
            } else {
                tempY = tempY % environnement[0].size
            }
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
        direction = Direction.IDLE
    }

    override fun toString(): String {
        return "Agent,$color,$x,$y,$direction"
    }

    fun calculateDValues(environnement: Array<Array<Agent?>>){
        //TODO
    }
}