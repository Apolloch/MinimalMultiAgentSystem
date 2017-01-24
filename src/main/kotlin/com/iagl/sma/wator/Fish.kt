package com.iagl.sma.wator

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import com.iagl.sma.core.pickRandomDirection
import com.iagl.sma.wator.PropertiesWator
import java.awt.Color
import java.security.SecureRandom


/**
 * Created by bacquet on 17/01/17.
 */
class Fish(x:Int, y:Int) : Agent(x,y,Color.GREEN) {
    private var tempX =0
    private var tempY =0
    private var oldX : Int
    private var oldY : Int
    private var direction = Direction.IDLE
    var time: Int
    var moved : Boolean

    init {
        oldY=y
        oldX=x
        moved = false
        time = SecureRandom().nextInt(6)
    }
    override fun decide(environnement: Array<Array<Agent?>>) {
        time++
        if(time>5)
            this.color = Color.BLUE
        if (tryToMove(environnement)){
            moved = true
        }
        if(moved && time % PropertiesWator.INSTANCE.fishBreedTime==0 ){
            reproduce(environnement)
        }
        moved = false
    }
    private fun reproduce(environnement: Array<Array<Agent?>>) {
        val child = Fish(oldX,oldY)
        Main.instance.addAgent(child)
    }

    private fun tryToMove(environnement: Array<Array<Agent?>>):Boolean{
        var allDirection = arrayListOf<Direction>()
        allDirection.addAll(Direction.values())
        var randomgen = SecureRandom()

        while (allDirection.size!=0){
            direction = allDirection.get(randomgen.nextInt(allDirection.size))
            if(direction!=Direction.IDLE) {
                tempX = x + direction.x
                tempY = y + direction.y

                if(PropertiesWator.INSTANCE.torique ) {
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

    override fun toString(): String {
        return "Fish,$color,$x,$y,$direction,$time"
    }
}
/**
 * Created by Nathan on 17/01/2017.
 */
