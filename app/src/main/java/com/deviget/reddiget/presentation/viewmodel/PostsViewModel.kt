package com.deviget.reddiget.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.repository.PostsRepository
import com.deviget.reddiget.data.repository.Resource

class PostsViewModel @ViewModelInject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    private val refreshSignal = MutableLiveData<Unit>()

    private val resource = refreshSignal.switchMap { repository.topPosts() }

    init {
        refresh()
    }

    val posts: LiveData<List<Post>> = resource.map { resource ->
        resource.data.orEmpty()
    }

    val refreshing: LiveData<Boolean> = resource.map { resource ->
        resource is Resource.Loading
    }

    fun refresh() {
        refreshSignal.value = Unit
    }
}