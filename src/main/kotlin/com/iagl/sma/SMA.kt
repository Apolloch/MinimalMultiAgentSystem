package com.iagl.sma

import java.awt.Color
import java.security.SecureRandom
import java.util.*

/**
 * Created by Nathan on 09/01/2017.
 */

class SMA(): Observable() {
    var environnement: Array<Array<Agent?>>
    var properties : Properties
    init {
        properties = loadProperties("properties.json")
        environnement = Array<Array<Agent?>>(properties.gridSizeX,{ it->Array<Agent?>(properties.gridSizeY,{ it -> null})})
        environnement[0][0] = Agent(0,0, Color.BLUE)
    }

    fun addAgent(agent : Agent){
        environnement[agent.x][agent.y]=agent
        setChanged()
        notifyObservers()
    }

    fun run(){
        init()
        var iterationCount = 0
        while ( properties.nbTicks==0 || iterationCount<properties.nbTicks){
            environnement.forEach { it.forEach { if(it!=null) it.decide(environnement) } }
            Thread.sleep(properties.delay.toLong())
            if(properties.trace)
                println("Tick")
            iterationCount++
            setChanged()
            notifyObservers()
        }
    }

    fun init(){
        var agentCount = 0
        var x : Int
        var y : Int
        var randomGenerator = SecureRandom()
        while (agentCount < properties.nbParticles){
            x = randomGenerator.nextInt(properties.gridSizeX)
            y = randomGenerator.nextInt(properties.gridSizeY)
            if(environnement[x][y]==null){
                environnement[x][y] = Agent(x,y,Color.RED)
                agentCount++
            }
        }
    }
}

fun main(args: Array<String>) {
    var sma = SMA()
    Vue("sma",sma)
    sma.run()
}