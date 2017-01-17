package com.iagl.sma

import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

/**
 * Created by Nathan on 10/01/2017.
 */
class Properties private constructor(){
        private val defaultPropertiesFile = "properties.json"

    var gridSizeX: Int
    var gridSizeY: Int
    var canvasSizeX: Int
    var canvasSizeY: Int
    var boxSize: Int
    var delay: Int
    var scheduling: Schedule
    var nbTicks: Int
    var grid: Boolean
    var trace: Boolean
    var seed: Int
    var refresh: Int
    var nbParticles: Int
    var torique: Boolean

    init {
            gridSizeX = 100
            gridSizeY = 100
            canvasSizeX = 500
            canvasSizeY = 500
            boxSize = 100
            delay = 50
            scheduling = Schedule.SEQUENTIEL
            nbTicks = 10
            grid = true
            trace = true
            seed = 0
            refresh = 10
            nbParticles = 10
            torique = false
            loadProperties(defaultPropertiesFile)
        }

    private object Holder { val INSTANCE = Properties() }
    companion object {
        val instance: Properties by lazy { Properties.Holder.INSTANCE }
    }
        fun loadProperties(propertiesFilePath: String) {
            var file = File(propertiesFilePath)

            if (file.exists()) {
                var content = ""
                file.forEachLine { content += it }
                var propertiesJson = JSONObject(content)
                try {

                    gridSizeX = propertiesJson.getInt("gridSizeX")
                    gridSizeY = propertiesJson.getInt("gridSizeY")
                    canvasSizeX = propertiesJson.getInt("canvasSizeX")
                    canvasSizeY = propertiesJson.getInt("canvasSizeY")
                    boxSize = propertiesJson.getInt("boxSize")
                    delay = propertiesJson.getInt("delay")
                    scheduling = Schedule.valueOf(propertiesJson.getString("scheduling"))
                    nbTicks = propertiesJson.getInt("nbTicks")
                    grid = propertiesJson.getBoolean("grid")
                    trace = propertiesJson.getBoolean("trace")
                    seed = propertiesJson.getInt("seed")
                    refresh = propertiesJson.getInt("refresh")
                    nbParticles = propertiesJson.getInt("nbParticles")
                    torique = propertiesJson.getBoolean("torique")

                } catch (e: JSONException) {
                    throw Exception("malformed properties file", e)
                }
            } else {
                throw FileNotFoundException("properties file not found")
            }

        }

}

enum class Schedule(){ EQUITABLE , SEQUENTIEL ,ALEATOIRE }

