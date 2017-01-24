package com.iagl.sma.wator

import com.iagl.sma.core.Agent
import com.iagl.sma.core.SMA
import com.iagl.sma.core.pickRandomDirection
import com.iagl.sma.wator.PropertiesWator
import java.awt.Color
import java.security.SecureRandom
import javax.swing.JFrame
import javax.swing.JScrollPane

/**
 * Created by bacquet on 17/01/17.
 */
class Main private constructor(): SMA(){
    private var fishCount : Int
    private var sharkCount : Int
    companion object {
        val instance : Main by lazy { Main() }
    }
    init {
        environnement = Array<Array<Agent?>>(PropertiesWator.INSTANCE.gridSizeX, { it -> Array<Agent?>(PropertiesWator.INSTANCE.gridSizeY, { it -> null }) })
        fishCount = 0
        sharkCount = 0
    }

    override fun addAgent(agent: Agent) {
        super.addAgent(agent)
        if(agent is Fish)
            fishCount++
        else
            sharkCount++
        if(PropertiesWator.INSTANCE.trace)
            println(agent)
    }

    override fun removeAgent(agent: Agent) {
        super.removeAgent(agent)
        if(agent is Fish)
            fishCount--
        else
            sharkCount--
        if(PropertiesWator.INSTANCE.trace)
            println(agent)
    }
    override fun init(){
        var sharkCount = 0
        var fishCount = 0
        var x : Int
        var y : Int
        var agent : Agent
        var randomGenerator = SecureRandom()
        while (sharkCount< PropertiesWator.INSTANCE.nbShark){
            x = randomGenerator.nextInt(PropertiesWator.INSTANCE.gridSizeX)
            y = randomGenerator.nextInt(PropertiesWator.INSTANCE.gridSizeY)
            if(environnement[x][y]==null){
                agent = Shark(x,y)
                addAgent(agent)
                sharkCount++
            }
        }
        while (fishCount< PropertiesWator.INSTANCE.nbFish){
            x = randomGenerator.nextInt(PropertiesWator.INSTANCE.gridSizeX)
            y = randomGenerator.nextInt(PropertiesWator.INSTANCE.gridSizeY)
            if(environnement[x][y]==null){
                agent = Fish(x,y)
                addAgent(agent)
                fishCount++
            }
        }
    }
    override fun run() {
        init()
        var iterationCount = 0
        while (PropertiesWator.INSTANCE.nbTicks == 0 || iterationCount < PropertiesWator.INSTANCE.nbTicks) {

            for(i in 0..agents.size-1){
                if(!diedAgents.contains(agents.get(i)))
                    agents.get(i).decide(environnement)
            }
            diedAgents.forEach { agents.remove(it) }
            diedAgents.clear()
            Thread.sleep(PropertiesWator.INSTANCE.delay.toLong())
            if (PropertiesWator.INSTANCE.trace)
                println("Tick,$iterationCount,$fishCount,$sharkCount")
            iterationCount++
            setChanged()
            notifyObservers()
        }
    }

}

fun main(args: Array<String>) {
    var sma = Main.instance
    var frame = JFrame("sma")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    var scrollPane = JScrollPane( Vue("sma",sma) )
    frame.contentPane = scrollPane
    frame.setSize(PropertiesWator.INSTANCE.canvasSizeX, PropertiesWator.INSTANCE.canvasSizeY)
    frame.isVisible = true
    sma.run()
}
