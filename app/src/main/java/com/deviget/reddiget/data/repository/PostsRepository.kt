package com.deviget.reddiget.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import calendar
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.webservice.RedditWebservice
import kotlinx.coroutines.delay

class PostsRepository(
    private val webservice: RedditWebservice
) {

    private var latest: List<Post>? = null

    fun topPosts(): LiveData<Resource<List<Post>>> = liveData {
        emit(Resource.Loading(latest))
        delay(2000)
        latest = (0..19).map { index ->
            Post(
                id = (0..9).map { ('a'..'z').random() }.joinToString(""),
                title = "Title $index",
                author = "Author $index",
                date = calendar().time,
                commentCount = (0..100).random(),
                read = listOf(true, false).random(),
                hidden = listOf(true, false).random(),
                text = "Post content $index",
                link = null,
                imageUrl = null
            )
        }
        emit(Resource.Success(latest))
    }
}