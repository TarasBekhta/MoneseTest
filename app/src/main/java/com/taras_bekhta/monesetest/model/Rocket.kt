package com.taras_bekhta.monesetest.model

import com.google.gson.annotations.SerializedName

data class Rocket(
    @SerializedName("rocket_id")
    var rocketId: String,

    @SerializedName("rocket_name")
    var rocketName: String,

    @SerializedName("wikipedia")
    var wikipediaUrl: String,

    @SerializedName("active")
    var isActive: Boolean,

    @SerializedName("stages")
    var stages: Int,

    @SerializedName("cost_per_launch")
    var costPerLaunch:Int,

    @SerializedName("description")
    var description: String
)