package com.deviget.reddiget.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.data.repository.PostsRepository

class PostsViewModel(
    private val repository: PostsRepository
) : ViewModel() {

    val posts: LiveData<List<Post>> = MutableLiveData()

    val refreshing: LiveData<Boolean> = MutableLiveData()

    fun refresh() {
    }
}