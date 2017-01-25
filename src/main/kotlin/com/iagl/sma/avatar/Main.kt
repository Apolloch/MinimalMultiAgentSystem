package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import com.iagl.sma.core.SMA
import java.security.SecureRandom
import javax.swing.JFrame
import javax.swing.JScrollPane

/**
 * Created by Apolloch on 20/01/2017.
 */

class Main : SMA() {
    var dValues : Array<Array<Int>>
    init{
        dValues = Array<Array<Int>>(PropertiesAvatar.INSTANCE.gridSizeX, { it -> Array<Int>(PropertiesAvatar.INSTANCE.gridSizeY, { it -> 0 }) })
        environnement = Array<Array<Agent?>>(PropertiesAvatar.INSTANCE.gridSizeX, { it -> Array<Agent?>(PropertiesAvatar.INSTANCE.gridSizeY, { it -> null }) })
        properties = PropertiesAvatar.INSTANCE
    }

    lateinit var avatar: Avatar

    override fun init() {
        var nbWall = 0
        var x :Int
        var y : Int
       var agent : Agent
        var randomGenerator = SecureRandom()
        while (nbWall<(PropertiesAvatar.INSTANCE.wallPercent*PropertiesAvatar.INSTANCE.gridSizeX*PropertiesAvatar.INSTANCE.gridSizeY)/100)
        {
            x = randomGenerator.nextInt(PropertiesAvatar.INSTANCE.gridSizeX)
            y = randomGenerator.nextInt(PropertiesAvatar.INSTANCE.gridSizeY)
            if(environnement[x][y]==null){
                agent = Wall(x,y)
                addAgent(agent)
                nbWall++
            }
        }
        var nbHunters = 0
        while (nbHunters<PropertiesAvatar.INSTANCE.nbHunter)
        {
            x = randomGenerator.nextInt(PropertiesAvatar.INSTANCE.gridSizeX)
            y = randomGenerator.nextInt(PropertiesAvatar.INSTANCE.gridSizeY)
            if(environnement[x][y]==null){
                agent = Hunter(x,y,dValues)
                addAgent(agent)
                nbHunters++
            }
        }

        var placed = false
        while (!placed)
        {
            x = randomGenerator.nextInt(PropertiesAvatar.INSTANCE.gridSizeX)
            y = randomGenerator.nextInt(PropertiesAvatar.INSTANCE.gridSizeY)
            if(environnement[x][y]==null){
                avatar = Avatar(x,y,dValues)
                addAgent(avatar)
                placed = true
            }
        }
    }

}

fun main(args: Array<String>) {
    var sma = Main()
    var frame = JFrame("sma")
    var vue = Vue("coucou",sma)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

    var scrollPane = JScrollPane( vue )
    frame.contentPane = scrollPane
    frame.setSize(PropertiesAvatar.INSTANCE.canvasSizeX, PropertiesAvatar.INSTANCE.canvasSizeY)
    frame.isVisible = true
    frame.addKeyListener(vue)
    sma.run()}