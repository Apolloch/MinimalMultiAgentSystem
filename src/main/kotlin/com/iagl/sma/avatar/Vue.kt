package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import com.iagl.sma.core.Direction
import com.iagl.sma.core.Properties
import com.iagl.sma.core.SMA
import java.awt.Color
import java.awt.Graphics
import java.awt.GridLayout
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane

/**
 * Created by Nathan on 09/01/2017.
 */
class Vue(title:String,sma: SMA): JPanel(), Observer,KeyListener {

    var gridPanel : JPanel
    var sma : Main
    private var nbLines = sma.environnement[0].size
    private var nbColumns = sma.environnement.size
    private var columnStep = width/nbColumns
        get() = width/nbColumns
    private var lineStep =height/nbLines
        get() = height/nbLines

    init {
        this.sma = sma as Main
        sma.addObserver(this)
        gridPanel= JPanel(GridLayout(1,1))
        this.addKeyListener(this)
    }

    override fun paint(g: Graphics?) {
        g?.color = Color.white
        g?.fillRect(0,0,width,height)
        updateGrid(sma,g)
        super.paintComponents(g)


    }

    private fun drawGrid(graphics: Graphics?) {
        if(PropertiesAvatar.INSTANCE.grid) {
            graphics?.color = Color.BLACK
            for (i in 1..nbColumns) {
                graphics?.drawLine(columnStep * i, 0, columnStep * i, nbLines * lineStep)
            }
            for (i in 1..nbLines) {
                graphics?.drawLine(0, lineStep * i, nbColumns * columnStep, lineStep * i)
            }
        }

    }

    private fun drawAgents(sma: SMA, graphics: Graphics?){
        sma.environnement.forEach {
            it.forEach {
                if(it != null) {
                    if (it is Wall)
                        drawWall(it,graphics)
                    else
                        drawAgent(it,graphics)
                }
            }
        }
    }

    private fun drawWall(agent: Agent, graphics: Graphics?) {
        graphics?.color = agent.color
        graphics?.fillRect(agent.x*columnStep,agent.y*lineStep,columnStep,lineStep)
    }
    private fun drawAgent(agent: Agent, graphics: Graphics?) {
        graphics?.color = agent.color
        graphics?.fillOval(agent.x*columnStep,agent.y*lineStep,columnStep,lineStep)
    }

    override fun update(o: Observable?, arg: Any?) {
        if(o is SMA){
            repaint()
        }
    }

    private fun  updateGrid(sma: SMA, graphics: Graphics?) {
        drawGrid(graphics)
        drawAgents(sma,graphics)
    }
    override fun keyPressed(e: KeyEvent?) {
        var direction : Direction
        println(KeyEvent.getKeyText(e!!.keyCode))
        when(e!!.keyCode){
            KeyEvent.VK_LEFT -> direction = Direction.LEFT
            KeyEvent.VK_RIGHT-> direction = Direction.RIGHT
            KeyEvent.VK_UP -> direction = Direction.TOP
            KeyEvent.VK_DOWN -> direction = Direction.BOTTOM
            else -> direction = sma.avatar.direction
        }

        sma.avatar.direction = direction
    }

    override fun keyReleased(e: KeyEvent?) {
    }

    override fun keyTyped(e: KeyEvent?) {
        println(KeyEvent.getKeyText(e!!.keyCode))
    }

}

