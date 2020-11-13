package com.deviget.reddiget.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

class PagedResource<T>(
    val pagedList: LiveData<PagedList<T>>,
    val status: LiveData<Status>
) {
    sealed class Status {
        object Idle : Status()
        object Loading : Status()
        class Error(val e: Throwable) : Status()
    }
}