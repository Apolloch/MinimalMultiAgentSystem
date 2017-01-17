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
class ParticulesSim : SMA(){

    override fun init(){
        var agentCount = 0
        var x : Int
        var y : Int
        var agent : Agent
        var randomGenerator = SecureRandom()
        while (agentCount < Properties.instance.nbParticles){
            x = randomGenerator.nextInt(Properties.instance.gridSizeX)
            y = randomGenerator.nextInt(Properties.instance.gridSizeY)
            if(environnement[x][y]==null){
                agent = Particule(x,y, Color((Math.random() * 0x1000000).toInt()).brighter() , pickRandomDirection())
                addAgent(agent)
                agentCount++
            }
        }
    }
}

fun main(args: Array<String>) {
    var sma = ParticulesSim()
    var frame = JFrame("sma")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    var scrollPane = JScrollPane( Vue("sma",sma) )
    frame.contentPane = scrollPane
    frame.setSize(Properties.instance.canvasSizeX, Properties.instance.canvasSizeY)
    frame.isVisible = true
    sma.run()
}
