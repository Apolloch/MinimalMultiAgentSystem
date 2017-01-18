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
     var diedAgents= arrayListOf<Agent>()

    init {
        Properties.instance.loadProperties("properties.json")
    }

    open fun addAgent(agent: Agent) {
        agents.add(agent)
        environnement[agent.x][agent.y] = agent
    }

    open fun removeAgent(agent: Agent) {
        diedAgents.add(agent)
        environnement[agent.x][agent.y] = null
    }

    open fun run() {
        init()
        var iterationCount = 0
        while (Properties.instance.nbTicks == 0 || iterationCount < Properties.instance.nbTicks) {

            for(i in 0..agents.size-1){
                if(!diedAgents.contains(agents.get(i)))
                    agents.get(i).decide(environnement)
            }
            diedAgents.forEach { agents.remove(it) }
            diedAgents.clear()
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
