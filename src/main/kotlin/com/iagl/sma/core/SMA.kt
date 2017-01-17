package com.iagl.sma.core

import com.iagl.sma.core.Properties
import java.awt.Color
import java.security.SecureRandom
import java.util.*
import javax.swing.JFrame
import javax.swing.JScrollPane

/**
 * Created by Nathan on 09/01/2017.
 */

abstract class SMA(): Observable() {
    lateinit var environnement: Array<Array<Agent?>>
    var agents = arrayListOf<Agent>()

    init {
        Properties.instance.loadProperties("properties.json")
    }

    fun addAgent(agent: Agent) {
        agents.add(agent)
        environnement[agent.x][agent.y] = agent
    }

    fun run() {
        init()
        var iterationCount = 0
        while (Properties.instance.nbTicks == 0 || iterationCount < Properties.instance.nbTicks) {
            agents.forEach { it.decide(environnement) }
            Thread.sleep(Properties.instance.delay.toLong())
            if (Properties.instance.trace)
                println("Tick")
            iterationCount++
            setChanged()
            notifyObservers()
        }
    }

    abstract fun init()
}
