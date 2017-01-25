package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import com.iagl.sma.core.vonNeumanNeighbourhood
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*

/**
 * Created by Apolloch on 20/01/2017.
 */

class Avatar(x :Int , y : Int, val dValues : Array<Array<Int>>):Agent(x,y, Color.BLUE),KeyListener {
    var age: Int
    var direction: Direction
    private var tempY: Int
    private var tempX: Int

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
        calculateDValues(environnement)
        if (age % speed == 0) {
            tempX = x + direction.x
            tempY = y + direction.y

            if (tempX >= 0 && tempX < environnement.size && tempY >= 0 && tempY < environnement[0].size) {
                if (environnement[tempX][tempY] == null) {
                    environnement[x][y] = null
                    x = tempX
                    y = tempY
                    environnement[tempX][tempY] = this
                } else {
                    particleCollisionGestion(environnement)
                }
            } else {
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

    fun calculateDValues(environnement: Array<Array<Agent?>>) {

        var toExploreNodes = ArrayList<Pair<Int, Int>>()
        for (i in 0..dValues.size - 1) {
            for (j in 0..dValues[0].size - 1) {
                dValues[i][j] = Math.max(dValues.size, dValues[0].size)
            }
        }
        dValues[x][y] = 0
        toExploreNodes.add(Pair(x, y))
        while (!toExploreNodes.isEmpty()) {
            toExploreNodes.addAll(calcNeibourhood(environnement, toExploreNodes.get(0).first, toExploreNodes.get(0).second))
            toExploreNodes.removeAt(0)
            toExploreNodes.sortBy { if (dValues[it.first][it.second] != -1) dValues[it.first][it.second] else Math.max(dValues.size, dValues[0].size) }

        }


//        println()
//        for (i in 0..dValues[0].size - 1) {
//            println()
//            for (j in 0..dValues.size - 1) {
//                print("${dValues[j][i]} ")
//            }
//        }
    }

    private fun calcNeibourhood(environnement: Array<Array<Agent?>>, nodeX: Int, nodeY: Int): ArrayList<Pair<Int, Int>> {
        val toExploreNodes = ArrayList<Pair<Int, Int>>()
        for (dir in vonNeumanNeighbourhood()) {
            if (nodeX + dir.x > 0 && nodeX + dir.x < environnement.size && nodeY + dir.y > 0 && nodeY + dir.y < environnement.size) {
                if (environnement[nodeX + dir.x][nodeY + dir.y] == null) {
                    if ((dValues[nodeX + dir.x][nodeY + dir.y] != -1 && dValues[nodeX + dir.x][nodeY + dir.y] > dValues[nodeX][nodeY] + 1)) {
                        dValues[nodeX + dir.x][nodeY + dir.y] = dValues[nodeX][nodeY] + 1
                        toExploreNodes.add(Pair(nodeX + dir.x, nodeY + dir.y))
                    }
                } else if (dValues[nodeX + dir.x][nodeY + dir.y] == 0){

                }
                else {
                    dValues[nodeX + dir.x][nodeY + dir.y] = -1
                }
            }
        }
        return toExploreNodes
    }

    override fun keyPressed(e: KeyEvent?) {
        var direction : Direction
        println(KeyEvent.getKeyText(e!!.keyCode))
        when(e!!.keyCode){
            KeyEvent.VK_LEFT -> direction = Direction.LEFT
            KeyEvent.VK_RIGHT-> direction = Direction.RIGHT
            KeyEvent.VK_UP -> direction = Direction.TOP
            KeyEvent.VK_DOWN -> direction = Direction.BOTTOM
            else -> direction = this.direction
        }

        this.direction = direction
    }

    override fun keyReleased(e: KeyEvent?) {
    }

    override fun keyTyped(e: KeyEvent?) {
        println(KeyEvent.getKeyText(e!!.keyCode))
    }

}