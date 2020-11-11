package com.deviget.reddiget.data.webservice.retrofit

import com.deviget.reddiget.data.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

/**
 * Parses a complex retrofit [Response] into a simple [DataResult] object
 */
suspend fun <T> retrofitRequest(request: suspend () -> Response<T>): DataResult<T> =
    try {
        request().run {
            if (isSuccessful) {
                val body = body()
                if (body != null) DataResult.Success(body)
                else DataResult.Failure(IllegalStateException("Null response body"))
            } else {
                val errorBody = errorBody()
                DataResult.Failure(
                    if (errorBody != null) IOException(withContext(Dispatchers.IO) { errorBody.string() })
                    else IllegalStateException("Null response errorBody")
                )
            }
        }
    } catch (e: Throwable) {
        DataResult.Failure(e)
    }
