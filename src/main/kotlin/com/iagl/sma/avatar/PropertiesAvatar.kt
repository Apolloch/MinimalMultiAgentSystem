package com.iagl.sma.avatar

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
class PropertiesAvatar private constructor():Properties(){
    private val defaultPropertiesFile = "propertiesAvatar.json"

    override var gridSizeX: Int = 0
    override var gridSizeY: Int = 0
    override var canvasSizeX: Int = 0
    override var canvasSizeY: Int = 0
    override var delay: Int = 0
    override var nbTicks: Int = 0
    override var grid: Boolean = false
    override var trace: Boolean = false
    override var torique: Boolean = false

    var wallPercent : Int = 0
    var nbHunter : Int = 0
    var speedHunter : Int = 0
    var speedAvatar : Int = 0
    var defenderLife : Int = 0

    private object Holder { val INSTANCE = PropertiesAvatar() }
    companion object {
        val INSTANCE: PropertiesAvatar by lazy { Holder.INSTANCE }
    }

    init {
        loadProperties(defaultPropertiesFile)
    }
    override fun loadProperties(propertiesFilePath: String):JSONObject {
        var propertiesJson = super.loadProperties(propertiesFilePath)
        try {

            wallPercent= propertiesJson.getInt("wallPercent")
            nbHunter = propertiesJson.getInt("nbHunter")
            speedAvatar = propertiesJson.getInt("speedAvatar")
            speedHunter = propertiesJson.getInt("speedHunter")
            defenderLife = propertiesJson.getInt("defenderLife")

        } catch (e: JSONException) {
            throw Exception("malformed properties file", e)
        }

        return propertiesJson
    }

}


