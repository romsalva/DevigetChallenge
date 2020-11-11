package com.deviget.reddiget.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deviget.reddiget.dependencyinjection.DependencyProvider
import com.deviget.reddiget.presentation.viewmodel.PostsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        PostsViewModel::class.java -> DependencyProvider.postsViewModel()
        else -> throw IllegalArgumentException("${modelClass.name} not supported")
    } as T
}