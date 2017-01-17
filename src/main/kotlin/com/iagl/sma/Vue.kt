package com.iagl.sma

import java.awt.Color
import java.awt.Graphics
import java.awt.GridLayout
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane

/**
 * Created by Nathan on 09/01/2017.
 */
//JPanel?
class Vue(title:String,sma: SMA):JPanel(),Observer{

    var gridPanel : JPanel
    var sma : SMA
    private var nbLines = sma.environnement[0].size
    private var nbColumns = sma.environnement.size
    private var columnStep = width/nbColumns
        get() = width/nbColumns
    private var lineStep =height/nbLines
        get() = height/nbLines

    init {
        this.sma = sma
        sma.addObserver(this)
        gridPanel= JPanel(GridLayout(1,1))
    }

    override fun paint(g: Graphics?) {
        g?.color = Color.white
        g?.fillRect(0,0,width,height)
        updateGrid(sma,g)
        super.paintComponents(g)


    }

    private fun drawGrid(graphics: Graphics?) {
        if(Properties.instance.grid) {
            graphics?.color = Color.BLACK
            for (i in 1..nbColumns) {
                graphics?.drawLine(columnStep * i, 0, columnStep * i, nbLines * lineStep)
            }
            for (i in 1..nbLines) {
                graphics?.drawLine(0, lineStep * i, nbColumns * columnStep, lineStep * i)
            }
        }

    }

    private fun drawParticules(sma: SMA,graphics: Graphics?){
        sma.environnement.forEach {
            it.forEach {
                if(it != null) {
                    drawParticule(it,graphics)
                }
            }
        }
    }

    private fun  drawParticule(agent: Agent,graphics:Graphics?) {
        graphics?.color = agent.color
        graphics?.fillOval(agent.x*columnStep,agent.y*lineStep,columnStep,lineStep)
    }

    override fun update(o: Observable?, arg: Any?) {
        if(o is SMA){
            repaint()
        }
    }

    private fun  updateGrid(sma: SMA,graphics: Graphics?) {
        drawGrid(graphics)
        drawParticules(sma,graphics)
    }

}

