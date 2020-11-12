package com.deviget.reddiget.presentation.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.repository.PostsRepository
import com.deviget.reddiget.data.repository.Resource

class PostViewModel(
    private val repository: PostsRepository
) : ViewModel() {

    private val postId = MutableLiveData<String>()
    private val resource = postId.switchMap { id ->
        if (id.isNotEmpty()) {
            repository.postById(id)
        } else {
            MutableLiveData(Resource.Error(IllegalArgumentException("Id must not be empty")))
        }
    }

    val post: LiveData<Post?> = resource.map { resource ->
        resource.data
    }

    val refreshing: LiveData<Boolean> = resource.map { resource ->
        resource is Resource.Loading
    }

    @MainThread
    fun setId(id: String) {
        postId.value = id
    }

}