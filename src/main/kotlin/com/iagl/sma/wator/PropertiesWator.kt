package com.iagl.sma.wator

import com.iagl.sma.core.Properties
import com.iagl.sma.core.Schedule
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import kotlin.properties.Delegates

/**
 * Created by Nathan on 10/01/2017.
 */
class PropertiesWator private constructor():Properties(){
    private val defaultPropertiesFile = "propertiesWator.json"

    override var gridSizeX: Int = 0
    override var gridSizeY: Int = 0
    override var canvasSizeX: Int = 0
    override var canvasSizeY: Int = 0
    override var delay: Int = 0
    override var nbTicks: Int = 0
    override var grid: Boolean = false
    override var trace: Boolean = false
    override var torique: Boolean = false

    var nbShark: Int = 0
    var sharkBreedTime : Int = 0
    var sharkStarveTime : Int = 0
    var fishBreedTime : Int = 0
    var nbFish: Int = 0

    private object Holder { val INSTANCE = PropertiesWator() }
    companion object {
        val INSTANCE: PropertiesWator by lazy { Holder.INSTANCE }
    }

    init {
        loadProperties(defaultPropertiesFile)
    }
    override fun loadProperties(propertiesFilePath: String):JSONObject {
        var propertiesJson = super.loadProperties(propertiesFilePath)
        try {

            nbShark = propertiesJson.getInt("nbShark")
            nbFish = propertiesJson.getInt("nbFish")
            sharkBreedTime = propertiesJson.getInt("sharkBreedTime")
            sharkStarveTime = propertiesJson.getInt("sharkStarveTime")
            fishBreedTime = propertiesJson.getInt("fishBreedTime")

        } catch (e: JSONException) {
            throw Exception("malformed properties file", e)
        }

        return propertiesJson
    }

}


