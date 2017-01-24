package com.iagl.sma.particules

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Properties
import com.iagl.sma.core.SMA
import com.iagl.sma.core.pickRandomDirection
import java.awt.Color
import java.security.SecureRandom
import javax.swing.JFrame
import javax.swing.JScrollPane

/**
 * Created by bacquet on 17/01/17.
 */
class Main : SMA(){

    /**
     * Created by bacquet on 17/01/17.
     */
        companion object {
            val instance : Main by lazy { Main() }
        }
        init {
            properties = PropertiesParticules.INSTANCE
            environnement = Array<Array<Agent?>>(properties.gridSizeX, { it -> Array<Agent?>(properties.gridSizeY, { it -> null }) })

        }
    override fun init(){
        var agentCount = 0
        var x : Int
        var y : Int
        var agent : Particule
        var randomGenerator = SecureRandom()
        while (agentCount < (properties as PropertiesParticules).nbParticules){
            x = randomGenerator.nextInt(properties.gridSizeX)
            y = randomGenerator.nextInt(properties.gridSizeY)
            if(environnement[x][y]==null){
                agent = Particule(x,y, Color((Math.random() * 0x1000000).toInt()).brighter() , pickRandomDirection())
                addAgent(agent)
                agentCount++
            }
        }
    }
}

fun main(args: Array<String>) {
    var sma = Main()
    var frame = JFrame("sma")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    var scrollPane = JScrollPane( Vue("sma",sma) )
    frame.contentPane = scrollPane
    frame.setSize(PropertiesParticules.INSTANCE.canvasSizeX, PropertiesParticules.INSTANCE.canvasSizeY)
    frame.isVisible = true
    sma.run()
}
