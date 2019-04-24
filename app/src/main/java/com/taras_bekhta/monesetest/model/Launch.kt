package com.taras_bekhta.monesetest.model

import com.google.gson.annotations.SerializedName

data class Launch(
    @SerializedName("flight_number")
    var flightNumber: Int = -1,

    @SerializedName("mission_name")
    var missionName: String = "",

    @SerializedName("launch_date_unix")
    var launchDateUTC: Long = -1,

    @SerializedName("rocket")
    var rocketInfo: RocketInfo,

    @SerializedName("mission_id")
    var missionId: ArrayList<String> = arrayListOf()
)

data class RocketInfo(
    @SerializedName("rocket_id")
    var rocketId: String
)