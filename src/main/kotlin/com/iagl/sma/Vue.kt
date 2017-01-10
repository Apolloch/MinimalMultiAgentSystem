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

class Vue(title:String,sma: SMA):JFrame(title),Observer{

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        var scrollPane = JScrollPane(gridPanel)
//        scrollPane.add(gridPanel)
        contentPane = scrollPane
        isVisible = true
        setSize(500,500)
        drawGrid()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        updateGrid(sma)

    }

    private fun drawGrid() {
        val graphics = gridPanel.graphics
        graphics.color = Color.BLACK
        for(i in 1..nbColumns){
            graphics.drawLine(columnStep*i,0,columnStep*i,nbLines*lineStep)
        }
        for(i in 1..nbLines){
            graphics.drawLine(0,lineStep*i,nbColumns*columnStep,lineStep*i)
        }

    }

    private fun drawParticules(sma: SMA){
        sma.environnement.forEach {
            it.forEach {
                if(it != null) {
                    drawParticule(it)
                }
            }
        }
    }

    private fun  drawParticule(agent: Agent) {
        val graphics = gridPanel.graphics
        graphics.color = agent.color
        graphics.fillOval(agent.x*columnStep,agent.y*lineStep,columnStep,lineStep)
    }

    override fun update(o: Observable?, arg: Any?) {
        if(o is SMA){
            updateGrid(o)
        }
    }

    private fun  updateGrid(sma: SMA) {
        drawGrid()
        drawParticules(sma)
    }

}

fun main(args: Array<String>) {
    val sma = SMA(10, 10)
    Vue("coucou", sma)
    sma.addAgent(Agent(9,9, Color.RED))
}
