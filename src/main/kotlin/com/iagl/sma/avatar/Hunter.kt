package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import com.iagl.sma.core.vonNeumanNeighbourhood
import java.awt.Color

/**
 * Created by Apolloch on 20/01/2017.
 */

class Hunter(x :Int , y : Int,val dValues : Array<Array<Int>>):Agent(x,y, Color.RED) {
    var age : Int
    var speed : Int
    init {
        age = 0
        speed =PropertiesAvatar.INSTANCE.speedHunter
    }
    override fun decide(environnement: Array<Array<Agent?>>) {

        var nextX = x
        var nextY = y
        var nextValue = Math.max(environnement.size, environnement[0].size)
        age++
        if (age % speed == 0) {
            for (direction in vonNeumanNeighbourhood()) {
                if (x + direction.x > 0 && x + direction.x < environnement.size && y + direction.y > 0 && y + direction.y < environnement.size) {
                    if (environnement[x + direction.x][y + direction.y] == null) {
                        if (dValues[x + direction.x][y + direction.y] < nextValue && dValues[x + direction.x][y + direction.y] != -1) {
                            nextValue = dValues[x + direction.x][y + direction.y]
                            nextX = x + direction.x
                            nextY = y + direction.y
                        }
                    }else if(dValues[x + direction.x][y + direction.y]==0) {
                        PropertiesAvatar.INSTANCE.nbTicks = 1
                        println("perdu")
                    }
                }
            }
            environnement[x][y] = null
            x = nextX
            y = nextY
            environnement[nextX][nextY] = this
        }
    }
}