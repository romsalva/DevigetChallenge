package com.deviget.reddiget.presentation.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.presentation.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private lateinit var views: Views
    private val viewModel by viewModels<PostsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        val adapter = PostsAdapter(
            onPostClicked = { post, _ -> clickPost(post) },
            onPostDismissed = { post, _ -> dismissPost(post) }
        )

        views.list.adapter = adapter
        views.list.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        views.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.posts.observe(viewLifecycleOwner) { posts ->
            //TODO: add "empty" view to fragment_posts
            adapter.submitList(posts)
        }
        viewModel.refreshing.observe(viewLifecycleOwner) { refreshing ->
            views.swipeRefresh.isRefreshing = refreshing ?: false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.posts, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_dismiss_all -> {
            dismissAll()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun clickPost(post: Post) {
        if (resources.getBoolean(R.bool.side_by_side)) {
            val navHostFragment = activity?.let {
                it.supportFragmentManager.findFragmentById(R.id.fragment_container_post) as NavHostFragment
            }
            navHostFragment?.navController?.navigate(
                R.id.destination_post,
                PostFragmentArgs(post.id).toBundle(),
                navOptions { launchSingleTop = true }
            )
        } else {
            findNavController().navigate(PostsFragmentDirections.actionPostsFragmentToPostFragment(post.id))
        }
    }

    private fun dismissPost(post: Post) {
        viewModel.hide(post)
    }

    private fun dismissAll() {
        viewModel.hideAllRead()
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