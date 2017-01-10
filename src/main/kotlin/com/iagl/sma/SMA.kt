package com.iagl.sma

import java.awt.Color
import java.util.*

/**
 * Created by Nathan on 09/01/2017.
 */

class SMA(var width : Int , var height : Int): Observable() {
    var environnement: Array<Array<Agent?>>
    init {
        environnement = Array<Array<Agent?>>(width,{ it->Array<Agent?>(height,{ it -> null})})
        environnement[0][0] = Agent(0,0, Color.BLUE)
    }

    fun addAgent(agent : Agent){
        environnement[agent.x][agent.y]=agent
        setChanged()
        notifyObservers()
    }

    fun run(){
        while (true){
            environnement.forEach { it.forEach { if(it!=null) it.decide(environnement) } }
        }
    }
}