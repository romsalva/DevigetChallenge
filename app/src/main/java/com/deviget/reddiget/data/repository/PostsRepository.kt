package com.deviget.reddiget.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.deviget.reddiget.data.DataResult
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.room.dao.PostsDao
import com.deviget.reddiget.data.webservice.RedditWebservice
import javax.inject.Inject

/**
 * This layer acts as the single source of truth for Posts.
 * It combines local storage and network calls into a unified data stream.
 */
class PostsRepository @Inject constructor(
    private val dao: PostsDao,
    private val webservice: RedditWebservice
) {

    fun topPosts(): LiveData<Resource<List<Post>>> = liveData {
        val allPosts = dao.allPosts()
        emitSource(allPosts.asLiveData().map { Resource.Loading(it) })
        when (val result = webservice.top()) {
            is DataResult.Success -> {
                dao.insert(result.data)
                emitSource(allPosts.asLiveData().map { Resource.Success(it) })
            }
            is DataResult.Failure -> emit(Resource.Error<List<Post>>(result.throwable))
        }
    }

    fun postById(id: String): LiveData<Resource<Post>> = liveData {
        val post = dao.postById(id)
        emitSource(post.asLiveData().map { Resource.Success(it) })
    }
}
