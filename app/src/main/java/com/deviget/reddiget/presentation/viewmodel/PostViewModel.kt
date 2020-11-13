package com.deviget.reddiget.presentation.viewmodel

import androidx.annotation.MainThread
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.repository.PostsRepository

class PostViewModel @ViewModelInject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    private val postId = MutableLiveData<String>()

    val post: LiveData<Post?> = postId.switchMap { id ->
        repository.postById(id).also {
            repository.setRead(id, true)
        }
    }

    @MainThread
    fun setId(id: String) {
        postId.value = id
    }

    fun hide() {
        postId.value?.let { repository.setHidden(it, true) }
    }

}