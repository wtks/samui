package work.wtks.samui

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AirconState(
    val mode: Mode,
    val temp: Int = 24,
    val vol: Vol = Vol.Auto,
    val dir: Dir = Dir.Auto
) {
    @Serializable
    enum class Mode {
        @SerialName("off")
        OFF,

        @SerialName("cooler")
        COOLER,

        @SerialName("heater")
        HEATER,

        @SerialName("dehumidifier")
        DEHUMIDIFIER
    }

    @Serializable
    enum class Vol {
        @SerialName("auto")
        Auto,

        @SerialName("0")
        V0,

        @SerialName("1")
        V1,

        @SerialName("2")
        V2,

        @SerialName("3")
        V3,

        @SerialName("4")
        V4,

        @SerialName("5")
        V5
    }

    @Serializable
    enum class Dir {
        @SerialName("auto")
        Auto,

        @SerialName("1")
        V1,

        @SerialName("2")
        V2,

        @SerialName("3")
        V3,

        @SerialName("4")
        V4,

        @SerialName("5")
        V5
    }

    fun toBody() = Json.encodeToString(this).encodeToByteArray()
}
