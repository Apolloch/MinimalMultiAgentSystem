package com.iagl.sma

import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException

/**
 * Created by Nathan on 10/01/2017.
 */
data class Properties (
        var gridSizeX : Int ,
        var gridSizeY : Int ,
        var canvasSizeX : Int ,
        var canvasSizeY : Int ,
        var boxSize : Int ,
        var delay : Int ,
        var scheduling : Schedule ,
        var nbTicks : Int ,
        var grid : Boolean ,
        var trace : Boolean ,
        var seed : Int ,
        var refresh : Int ,
        var nbParticles : Int,
        var torique : Boolean
)


enum class Schedule(){ EQUITABLE , SEQUENTIEL ,ALEATOIRE }

fun loadProperties(propertiesFilePath : String):Properties {
    var file = File(propertiesFilePath)
    if (file.exists()) {
        var content = ""
        file.forEachLine { content+=it }
        var propertiesJson = JSONObject(content)
        try {
            return Properties(
                    propertiesJson.getInt("gridSizeX"),
                    propertiesJson.getInt("gridSizeY"),
                    propertiesJson.getInt("canvasSizeX"),
                    propertiesJson.getInt("canvasSizeY"),
                    propertiesJson.getInt("boxSize"),
                    propertiesJson.getInt("delay"),
                    Schedule.valueOf(propertiesJson.getString("scheduling")),
                    propertiesJson.getInt("nbTicks"),
                    propertiesJson.getBoolean("grid"),
                    propertiesJson.getBoolean("trace"),
                    propertiesJson.getInt("seed"),
                    propertiesJson.getInt("refresh"),
                    propertiesJson.getInt("nbParticles"),
                    propertiesJson.getBoolean("torique")
            )
        }
        catch (e : JSONException){
            throw Exception("malformed properties file",e)
        }
    }
    else{
        throw FileNotFoundException("properties file not found")
    }

}