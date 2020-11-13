package com.deviget.reddiget.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.repository.PagedResource
import com.deviget.reddiget.data.repository.PostsRepository

class PostsViewModel @ViewModelInject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    private val refreshSignal = MutableLiveData<RefreshSignal>()

    private val resource = refreshSignal.map { signal ->
        repository.topPosts(signal is RefreshSignal.Forced)
    }

    init {
        refreshSignal.value = RefreshSignal.Initial
    }

    val posts: LiveData<PagedList<Post>> = resource.switchMap { resource ->
        resource.pagedList
    }

    val refreshing: LiveData<Boolean> = resource.switchMap { resource ->
        resource.status.map { it == PagedResource.Status.Loading }
    }

    fun hide(post: Post) {
        repository.setHidden(post.id, true)
    }

    fun refresh() {
        refreshSignal.value = RefreshSignal.Forced
    }

    sealed class RefreshSignal {
        object Initial : RefreshSignal()
        object Forced : RefreshSignal()
    }
}