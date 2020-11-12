package com.deviget.reddiget.dependencyinjection

import com.deviget.reddiget.Configuration
import com.deviget.reddiget.data.repository.PostsRepository
import com.deviget.reddiget.data.webservice.RedditWebservice
import com.deviget.reddiget.data.webservice.retrofit.RedditService
import com.deviget.reddiget.presentation.viewmodel.PostViewModel
import com.deviget.reddiget.presentation.viewmodel.PostsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Manual dependency injection.
 * This should be easily replaceable by a robust framework such as Hilt or Koin
 */
object DependencyProvider {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Configuration.redditUri.toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val retrofitService = retrofit.create<RedditService>()

    fun redditWebservice() = RedditWebservice(retrofitService)
    fun postsRepository() = PostsRepository(redditWebservice())
    fun postsViewModel() = PostsViewModel(postsRepository())
    fun postViewModel() = PostViewModel(postsRepository())
}
