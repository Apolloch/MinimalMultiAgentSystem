package com.iagl.sma

import java.awt.Color
import java.util.*

/**
 * Created by Nathan on 09/01/2017.
 */

class SMA(var width : Int , var height : Int): Observable() {
    var grid : Array<Array<Agent?>>
    init {
        grid = Array<Array<Agent?>>(width,{it->Array<Agent?>(height,{it -> null})})
        grid[0][0] = Agent(0,0, Color.BLUE)
    }

    fun addAgent(agent : Agent){
        grid[agent.x][agent.y]=agent
        setChanged()
        notifyObservers()
    }
}