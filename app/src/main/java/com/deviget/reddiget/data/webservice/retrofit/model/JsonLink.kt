package com.deviget.reddiget.data.webservice.retrofit.model

import com.google.gson.annotations.SerializedName

/**
 * I'd normally create a full representation of the endpoint's data,
 * In this case I'll skip it and only put the one's I need here.
 * Otherwise It'll take me a lot of time to check and validate type and nullability for each one.
 */
data class JsonLink(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("created_utc")
    val createdUtc: Long,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("post_hint")
    val postHint: String?,
    @SerializedName("num_comments")
    val numComments: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("selftext_html")
    val selfTextHtml: String?
)
