package com.deviget.reddiget.data.webservice

import calendar
import com.deviget.reddiget.data.DataResult
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.map
import com.deviget.reddiget.data.webservice.retrofit.RedditService
import com.deviget.reddiget.data.webservice.retrofit.retrofitRequest
import com.deviget.reddiget.presentation.extension.asUri
import timeInSeconds
import javax.inject.Inject

private const val THUMBNAIL_SELF = "self"
private const val THUMBNAIL_IMAGE = "image"
private const val THUMBNAIL_DEFAULT = "default"
private const val TYPE_IMAGE = "image"
private const val TYPE_LINK = "link"

/**
 * This layer hides any networking complexities and offers resources in the "datamodel" format.
 */
class RedditWebservice @Inject constructor(
    private val redditService: RedditService
) {

    suspend fun top(fullName: String? = null, count: Int? = null, limit: Int? = null): DataResult<List<Post>> =
        retrofitRequest {
            redditService.top(
                after = fullName,
                limit = limit,
                count = count
            )
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
                            THUMBNAIL_SELF -> null
                            THUMBNAIL_IMAGE -> null
                            THUMBNAIL_DEFAULT -> null
                            else -> thumbnail.asUri()
                        },
                        read = false,
                        hidden = false,
                        type = when (postHint) {
                            TYPE_IMAGE -> Post.Type.IMAGE
                            TYPE_LINK -> Post.Type.LINK
                            else -> Post.Type.OTHER
                        },
                        text = selfTextHtml,
                        link = url.asUri()
                    )
                }
            }
        }

}
