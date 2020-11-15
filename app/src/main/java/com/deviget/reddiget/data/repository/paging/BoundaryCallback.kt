package com.deviget.reddiget.data.repository.paging

import androidx.paging.PagedList

/**
 * Convenience wrapper for [PagedList.BoundaryCallback]
 */
class BoundaryCallback<T>(
    private val zeroItemsLoaded: () -> Unit = {},
    private val itemAtFrontLoaded: (item: T) -> Unit = {},
    private val itemAtEndLoaded: (item: T) -> Unit = {}
) : PagedList.BoundaryCallback<T>() {
    override fun onZeroItemsLoaded() {
        zeroItemsLoaded()
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        itemAtFrontLoaded(itemAtFront)
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        itemAtEndLoaded(itemAtEnd)
    }
}