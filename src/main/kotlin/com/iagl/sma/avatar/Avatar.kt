package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import java.awt.Color

/**
 * Created by Apolloch on 20/01/2017.
 */

class Avatar(x :Int , y : Int, val dValues : Array<Array<Int>>):Agent(x,y, Color.BLUE) {
    var age : Int
    var direction: Direction
    private var  tempY: Int
    private var tempX:Int

    private var speed: Int

    init {
        speed = PropertiesAvatar.INSTANCE.speedAvatar
        age = 0
        tempX = x
        tempY = y
        direction = Direction.IDLE
    }

    override fun decide(environnement: Array<Array<Agent?>>) {
        age++
        if(age % speed == 0){
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
    }

    fun borderGestion(environnement: Array<Array<Agent?>>) {
        if (!PropertiesAvatar.INSTANCE.torique)
            direction = Direction.IDLE
        if (PropertiesAvatar.INSTANCE.torique) {
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
        var distance = 1

        for( dir in Direction.values() ){
            if(x+dir.x > 0 && x+dir.x < environnement.size && y+dir.x > 0 && y+dir.x < environnement.size && dir!= Direction.IDLE)
                if(environnement[x+dir.x][y+dir.y] != null){
                    dValues[x+dir.x][y+dir.y] = distance
                }
                else{
                    dValues[x+dir.x][y+dir.y] = -1
                }
        }
    }
}