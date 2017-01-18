package com.iagl.sma.wator

import com.iagl.sma.core.Agent
import com.iagl.sma.core.SMA
import com.iagl.sma.core.pickRandomDirection
import com.iagl.sma.wator.Properties
import java.awt.Color
import java.security.SecureRandom
import javax.swing.JFrame
import javax.swing.JScrollPane

/**
 * Created by bacquet on 17/01/17.
 */
class Main private constructor(): SMA(){
    companion object {
        val instance : Main by lazy { Main() }
    }
    init {
        environnement = Array<Array<Agent?>>(Properties.instance.gridSizeX, { it -> Array<Agent?>(Properties.instance.gridSizeY, { it -> null }) })

    }
    override fun init(){
        var sharkCount = 0
        var fishCount = 0
        var x : Int
        var y : Int
        var agent : Agent
        var randomGenerator = SecureRandom()
        while (sharkCount< Properties.instance.nbShark){
            x = randomGenerator.nextInt(Properties.instance.gridSizeX)
            y = randomGenerator.nextInt(Properties.instance.gridSizeY)
            if(environnement[x][y]==null){
                agent = Shark(x,y)
                addAgent(agent)
                sharkCount++
            }
        }
        while (fishCount< Properties.instance.nbFish){
            x = randomGenerator.nextInt(Properties.instance.gridSizeX)
            y = randomGenerator.nextInt(Properties.instance.gridSizeY)
            if(environnement[x][y]==null){
                agent = Fish(x,y)
                addAgent(agent)
                fishCount++
            }
        }
    }
}

fun main(args: Array<String>) {
    var sma = Main.instance
    var frame = JFrame("sma")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    var scrollPane = JScrollPane( Vue("sma",sma) )
    frame.contentPane = scrollPane
    frame.setSize(Properties.instance.canvasSizeX, Properties.instance.canvasSizeY)
    frame.isVisible = true
    sma.run()
}
