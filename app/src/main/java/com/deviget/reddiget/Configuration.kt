package com.deviget.reddiget

import android.net.Uri
import androidx.paging.Config as PagingConfig

object Configuration {
    val databaseName = "posts_database"
    val redditUri = Uri.Builder().scheme("https").authority("api.reddit.com").build()

    val pagingConfig = PagingConfig(
        pageSize = 50,
        prefetchDistance = 5,
        enablePlaceholders = false,
        initialLoadSizeHint = 50
    )

}