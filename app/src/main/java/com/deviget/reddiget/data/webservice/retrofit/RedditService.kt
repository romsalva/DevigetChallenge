package com.deviget.reddiget.data.webservice.retrofit

import com.deviget.reddiget.data.webservice.retrofit.model.JsonLink
import com.deviget.reddiget.data.webservice.retrofit.model.JsonListing
import com.deviget.reddiget.data.webservice.retrofit.model.JsonThing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("/top")
    suspend fun top(
        @Query("after") after: String,
        @Query("before") before: String,
        @Query("limit") limit: Int,
        @Query("count") count: Int,
        @Query("show") show: String
    ): Response<JsonThing<JsonListing<JsonLink>>>

}