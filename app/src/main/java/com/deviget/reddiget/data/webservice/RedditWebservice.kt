package com.deviget.reddiget.data.webservice

import calendar
import com.deviget.reddiget.data.DataResult
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.map
import com.deviget.reddiget.data.webservice.retrofit.RedditService
import com.deviget.reddiget.data.webservice.retrofit.retrofitRequest
import com.deviget.reddiget.presentation.extension.asUri
import timeInSeconds

/**
 * This layer hides any networking complexities and offers resources in the "datamodel" format.
 */
class RedditWebservice(private val redditService: RedditService) {

    suspend fun top(): DataResult<List<Post>> =
        retrofitRequest {
            redditService.top()
        }.map { listing ->
            listing.data.children.map { thing ->
                with(thing.data) {
                    Post(
                        id = id,
                        title = title,
                        author = author,
                        date = calendar { timeInSeconds = createdUtc }.time,
                        commentCount = numComments,
                        thumbnail = when (thumbnail) {
                            "self" -> null
                            "image" -> null
                            "default" -> null
                            else -> thumbnail.asUri()
                        },
                        read = false,
                        hidden = false,
                        text = selfTextHtml,
                        link = url.asUri()
                    )
                }
            }
        }
}
