package com.deviget.reddiget.presentation.viewmodel

import androidx.lifecycle.*
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.repository.PostsRepository
import com.deviget.reddiget.data.repository.Resource

class PostsViewModel(
    private val repository: PostsRepository
) : ViewModel() {

    private val refreshSignal = MutableLiveData<Unit>()

    private val resource = refreshSignal.switchMap { repository.topPosts() }

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