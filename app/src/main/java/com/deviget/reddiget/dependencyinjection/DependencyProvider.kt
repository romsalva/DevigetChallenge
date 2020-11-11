package com.deviget.reddiget.dependencyinjection

import com.deviget.reddiget.data.repository.PostsRepository
import com.deviget.reddiget.data.webservice.RedditWebservice
import com.deviget.reddiget.presentation.viewmodel.PostsViewModel

/**
 * Manual dependency injection.
 * This should be easily replaceable by a robust framework such as Hilt or Koin
 */
object DependencyProvider {
    fun postsViewModel() = PostsViewModel(postsRepository())
    fun postsRepository() = PostsRepository(redditWebservice())
    fun redditWebservice() = RedditWebservice()
}
