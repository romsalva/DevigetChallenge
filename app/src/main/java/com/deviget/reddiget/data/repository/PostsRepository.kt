package com.deviget.reddiget.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.deviget.reddiget.data.DataResult
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.webservice.RedditWebservice

/**
 * This layer acts as the single source of truth for Posts.
 * It combines local storage and network calls into a unified data stream.
 */
class PostsRepository(
    private val webservice: RedditWebservice
) {

    //TODO: replace this with actual persistences
    private var latest: List<Post>? = null

    fun topPosts(): LiveData<Resource<List<Post>>> = liveData {
        emit(Resource.Loading(latest))
        val result = webservice.top()
        emit(
            when (result) {
                is DataResult.Success -> {
                    latest = result.data
                    Resource.Success(result.data)
                }
                is DataResult.Failure -> Resource.Error(result.throwable)
            }
        )
    }
}