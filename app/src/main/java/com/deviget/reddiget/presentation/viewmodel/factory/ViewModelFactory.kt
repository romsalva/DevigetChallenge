package com.deviget.reddiget.presentation.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deviget.reddiget.dependencyinjection.DependencyProvider
import com.deviget.reddiget.presentation.viewmodel.PostViewModel
import com.deviget.reddiget.presentation.viewmodel.PostsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        DependencyProvider.init(context)
        return when (modelClass) {
            PostsViewModel::class.java -> DependencyProvider.postsViewModel()
            PostViewModel::class.java -> DependencyProvider.postViewModel()
            else -> throw IllegalArgumentException("${modelClass.name} not supported")
        } as T
    }
}