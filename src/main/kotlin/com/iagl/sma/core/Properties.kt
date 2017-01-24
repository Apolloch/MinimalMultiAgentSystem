package com.iagl.sma.core

import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

/**
 * Created by Nathan on 10/01/2017.
 */
abstract class Properties (){

    abstract var gridSizeX: Int
    abstract var gridSizeY: Int
    abstract var canvasSizeX: Int
    abstract var canvasSizeY: Int
    abstract var delay: Int
    abstract var nbTicks: Int
    abstract var grid: Boolean
    abstract var trace: Boolean
    abstract var torique: Boolean


        open fun loadProperties(propertiesFilePath: String):JSONObject {
            var file = File(propertiesFilePath)
            var propertiesJson: JSONObject
            if (file.exists()) {
                var content = ""
                file.forEachLine { content += it }
                propertiesJson = JSONObject(content)
                try {

                    gridSizeX = propertiesJson.getInt("gridSizeX")
                    gridSizeY = propertiesJson.getInt("gridSizeY")
                    canvasSizeX = propertiesJson.getInt("canvasSizeX")
                    canvasSizeY = propertiesJson.getInt("canvasSizeY")
                    delay = propertiesJson.getInt("delay")
                    nbTicks = propertiesJson.getInt("nbTicks")
                    grid = propertiesJson.getBoolean("grid")
                    trace = propertiesJson.getBoolean("trace")
                    torique = propertiesJson.getBoolean("torique")

                } catch (e: JSONException) {
                    throw Exception("malformed properties file", e)
                }
            } else {
                throw FileNotFoundException("properties file not found")
            }
            return propertiesJson
        }

}

enum class Schedule(){ EQUITABLE , SEQUENTIEL ,ALEATOIRE }

