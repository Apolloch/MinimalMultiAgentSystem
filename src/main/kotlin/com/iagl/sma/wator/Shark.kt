package com.iagl.sma.wator

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import com.iagl.sma.core.pickRandomDirection
import com.iagl.sma.wator.Properties
import java.awt.Color
import java.security.SecureRandom

/**
 * Created by bacquet on 17/01/17.
 */
class Shark(x:Int, y:Int) : Agent(x,y, Color.BLACK) {
    private var tempX =0
    private var tempY =0
    private var direction = Direction.IDLE
    var breedTime : Int
    var moved : Boolean
    var starveTime : Int
    var oldX : Int
    var oldY : Int
    init {
        moved = false
        breedTime = 0
        starveTime = 0
        oldX = x
        oldY = y

    }

    override fun decide(environnement: Array<Array<Agent?>>) {
        breedTime++
        starveTime++
        if(seekAndDestroy(environnement)){
            moved = true
        }
        else if(tryToMove(environnement)){
            moved = true
        }
        if(moved && breedTime%Properties.instance.sharkBreedTime==0 ){
            reproduce(environnement)
        }
        if(starveTime==Properties.instance.sharkStarveTime) {
            Main.instance.removeAgent(this)
        }
        moved = false

    }

    private fun reproduce(environnement: Array<Array<Agent?>>) {
        if(Properties.instance.trace)
            println(this)
        val child = Shark(oldX,oldY)
        Main.instance.addAgent(child)
    }

    private fun tryToMove(environnement: Array<Array<Agent?>>):Boolean{
        var allDirection = arrayListOf<Direction>()
        allDirection.addAll(Direction.values())
        var randomgen =SecureRandom()
        while (allDirection.size!=0){
            val direction = allDirection.get(randomgen.nextInt(allDirection.size))
            if(direction!=Direction.IDLE) {

                tempX = x + direction.x
                tempY = y + direction.y

                if(Properties.instance.torique ) {
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
                }
                breedTime = (breedTime + 1) % Properties.instance.sharkBreedTime
                if (tempX >= 0 && tempX < environnement.size && tempY >= 0 && tempY < environnement[0].size) {
                    if (environnement[tempX][tempY] == null) {
                        environnement[x][y] = null
                        oldX = x
                        oldY = y
                        x = tempX
                        y = tempY
                        environnement[tempX][tempY] = this
                        return true
                    }
                }
            }
            allDirection.remove(direction)
        }
        return false
    }

    private fun seekAndDestroy(environnement: Array<Array<Agent?>>): Boolean {
        var tempX : Int
        var tempY : Int
        var haveEat = false
        var allDirection = arrayListOf<Direction>()
        allDirection.addAll(Direction.values())
        var randomgen =SecureRandom()
        while (allDirection.size!=0){
            val direction = allDirection.get(randomgen.nextInt(allDirection.size))
            tempX=x+direction.x
            tempY=y+direction.y
            if(Properties.instance.torique ) {
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
            }
            if( tempX>0 && tempX<environnement.size && tempY>0 && tempY<environnement[0].size) {
                if (environnement[tempX][tempY] != null && environnement[tempX][tempY] is Fish) {
                    eatHim(environnement[tempX][tempY] as Fish)
                    environnement[x][y]=null
                    oldX = x
                    oldY = y
                    x = tempX
                    y = tempY
                    environnement[tempX][tempY]=this
                    haveEat=true
                }
                break
            }
            allDirection.remove(direction)
        }
        return haveEat

    }

    private fun eatHim(fish : Fish) {
        starveTime = 0
        Main.instance.removeAgent(fish)

    }


        override fun toString(): String {
        return "Agent,$color,$x,$y,$direction,$breedTime,$starveTime"
    }
}