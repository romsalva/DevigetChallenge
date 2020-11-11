package com.deviget.reddiget.data.webservice.retrofit.model

import com.google.gson.annotations.SerializedName

data class JsonThing<T>(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("data")
    val data: T
)
