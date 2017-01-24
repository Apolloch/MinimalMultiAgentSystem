package com.iagl.sma.avatar

import com.iagl.sma.core.Agent
import java.awt.Color

/**
 * Created by Apolloch on 20/01/2017.
 */

class Wall(x :Int , y : Int):Agent(x,y, Color.DARK_GRAY) {
    override fun decide(environnement: Array<Array<Agent?>>) {

    }
}