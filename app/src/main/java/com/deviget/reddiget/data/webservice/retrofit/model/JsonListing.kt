package com.deviget.reddiget.data.webservice.retrofit.model

import com.google.gson.annotations.SerializedName

class JsonListing<T>(
    @SerializedName("modhash")
    val modhash: String,
    @SerializedName("dist")
    val dist: Int,
    @SerializedName("children")
    val children: List<JsonThing<T>>,
    @SerializedName("after")
    val after: String?,
    @SerializedName("before")
    val before: String?
)
