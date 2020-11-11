package com.deviget.reddiget.data.webservice.retrofit

import com.deviget.reddiget.data.webservice.retrofit.model.JsonLink
import com.deviget.reddiget.data.webservice.retrofit.model.JsonListing
import com.deviget.reddiget.data.webservice.retrofit.model.JsonThing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This interface should be the simplest possible representation of the endpoint.
 */
interface RedditService {

    @GET("/top")
    suspend fun top(
        @Query("after") after: String? = null,
        @Query("before") before: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("count") count: Int? = null,
        @Query("show") show: String? = null
    ): Response<JsonThing<JsonListing<JsonLink>>>

}
