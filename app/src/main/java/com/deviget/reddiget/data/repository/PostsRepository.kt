package com.deviget.reddiget.data.repository

import androidx.lifecycle.*
import androidx.paging.toLiveData
import com.deviget.reddiget.Configuration
import com.deviget.reddiget.data.DataResult
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.room.dao.PostsDao
import com.deviget.reddiget.data.webservice.RedditWebservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val PREFIX_T3 = "t3_"

/**
 * This layer acts as the single source of truth for Posts.
 * It combines local storage and network calls into a unified data stream.
 */
class PostsRepository @Inject constructor(
    private val dao: PostsDao,
    private val webservice: RedditWebservice
) {

    private val scope = object : CoroutineScope {
        val job = Job()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.Main
    }

    fun topPosts(forceRefresh: Boolean = false): PagedResource<Post> {
        val status = MutableLiveData<PagedResource.Status>(PagedResource.Status.Idle)

        fun fetch(clearDatabase: Boolean = false, request: suspend () -> DataResult<List<Post>>) {
            status.value = PagedResource.Status.Loading
            scope.launch {
                when (val result = request()) {
                    is DataResult.Success -> {
                        status.postValue(PagedResource.Status.Idle)
                        if (clearDatabase) {
                            dao.deleteAllPosts()
                        }
                        dao.insert(result.data)
                    }
                    is DataResult.Failure -> status.postValue(PagedResource.Status.Error(result.throwable))
                }
            }
        }

        val boundaryCallback = BoundaryCallback<Post>(
            zeroItemsLoaded = {
                fetch {
                    webservice.top(
                        limit = Configuration.pagingConfig.pageSize
                    )
                }
            },
            itemAtEndLoaded = { post ->
                fetch {
                    webservice.top(
                        fullName = PREFIX_T3 + post.id,
                        limit = Configuration.pagingConfig.pageSize
                    )
                }
            }
        )

        if (forceRefresh) {
            fetch(clearDatabase = true) {
                webservice.top(
                    limit = Configuration.pagingConfig.pageSize
                )
            }
        }

        return PagedResource(
            dao.allPosts().toLiveData(
                config = Configuration.pagingConfig,
                boundaryCallback = boundaryCallback
            ),
            status
        )
    }

    fun postById(id: String): LiveData<Resource<Post>> = liveData {
        val post = dao.postById(id)
        emitSource(post.asLiveData().map { Resource.Success(it) })
    }

}
