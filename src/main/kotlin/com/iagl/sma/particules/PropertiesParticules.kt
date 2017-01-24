package com.iagl.sma.particules

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
class PropertiesParticules private constructor():Properties(){
    private val defaultPropertiesFile = "properties.json"

    override var gridSizeX: Int = 0
    override var gridSizeY: Int = 0
    override var canvasSizeX: Int = 0
    override var canvasSizeY: Int = 0
    override var delay: Int = 0
    override var nbTicks: Int = 0
    override var grid: Boolean = false
    override var trace: Boolean = false
    override var torique: Boolean = false

    var nbParticules: Int = 0

    private object Holder { val INSTANCE = PropertiesParticules() }
    companion object {
        val INSTANCE: PropertiesParticules by lazy { Holder.INSTANCE }
    }

    init {
        loadProperties(defaultPropertiesFile)
    }
    override fun loadProperties(propertiesFilePath: String):JSONObject {
        var propertiesJson = super.loadProperties(propertiesFilePath)
        try {

            nbParticules = propertiesJson.getInt("nbParticules")

        } catch (e: JSONException) {
            throw Exception("malformed properties file", e)
        }

        return propertiesJson
    }

}


