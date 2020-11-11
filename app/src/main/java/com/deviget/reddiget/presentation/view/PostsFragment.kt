package com.deviget.reddiget.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deviget.reddiget.R
import com.deviget.reddiget.presentation.extension.toast
import com.deviget.reddiget.presentation.viewmodel.PostsViewModel
import com.deviget.reddiget.presentation.viewmodel.factory.ViewModelFactory

class PostsFragment : Fragment() {

    private lateinit var views: Views

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = Views(view)
        val adapter = PostsAdapter { post, position ->
            toast("Post with id '${post.id}' at position $position dismissed")
        }
        val viewModel by viewModels<PostsViewModel> { ViewModelFactory() }

        views.list.adapter = adapter
        views.list.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        views.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            //TODO: add "empty" view to fragment_posts
            adapter.setPosts(posts.orEmpty())
        }
        viewModel.refreshing.observe(viewLifecycleOwner) { refreshing ->
            views.swipeRefresh.isRefreshing = refreshing ?: false
        }

        viewModel.refresh()
    }

    private class Views(
        val swipeRefresh: SwipeRefreshLayout,
        val list: RecyclerView
    ) {
        constructor(view: View) : this(
            view.findViewById(R.id.swipe_refresh),
            view.findViewById(R.id.list)
        )
    }

}