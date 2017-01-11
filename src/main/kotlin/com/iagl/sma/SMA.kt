package com.iagl.sma

import java.awt.Color
import java.security.SecureRandom
import java.util.*

/**
 * Created by Nathan on 09/01/2017.
 */

class SMA(): Observable() {
    var environnement: Array<Array<Agent?>>
    var agents = arrayListOf<Agent>()
    var properties : Properties
    init {
        properties = loadProperties("properties.json")
        environnement = Array<Array<Agent?>>(properties.gridSizeX,{ it->Array<Agent?>(properties.gridSizeY,{ it -> null})})
    }

    fun addAgent(agent : Agent){
        println(agent)
        agents.add(agent)
        environnement[agent.x][agent.y]=agent
    }

    fun run(){
        init()
        var iterationCount = 0
        while ( properties.nbTicks==0 || iterationCount<properties.nbTicks){
            agents.forEach { it.decide(environnement) }
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
        var agent : Agent
        var randomGenerator = SecureRandom()
        while (agentCount < properties.nbParticles){
            x = randomGenerator.nextInt(properties.gridSizeX)
            y = randomGenerator.nextInt(properties.gridSizeY)
            if(environnement[x][y]==null){
                agent = Agent(x,y, Color((Math.random() * 0x1000000).toInt() ))
                addAgent(agent)
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