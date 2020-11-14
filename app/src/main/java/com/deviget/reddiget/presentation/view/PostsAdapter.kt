package com.deviget.reddiget.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post

private val diff = object : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}

class PostsAdapter(
    private val onAction: (post: Post, position: Int, action: PostAction) -> Unit
) : PagedListAdapter<Post, PostViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false),
            onAction = { position, action -> getItem(position)?.let { onAction(it, position, action) } }
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}
