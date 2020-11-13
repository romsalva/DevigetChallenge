package com.deviget.reddiget.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post

private val diff = object : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}

class PostsAdapter(
    private val onPostClicked: (post: Post, position: Int) -> Unit = { _, _ -> },
    private val onPostDismissed: (post: Post, position: Int) -> Unit = { _, _ -> }
) : PagedListAdapter<Post, PostViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false),
            onClick = { position -> getItem(position)?.let { onPostClicked(it, position) } },
            onDismiss = { position -> getItem(position)?.let { onPostDismissed(it, position) } }
        )

    //TODO: hardcoded strings will be fixed when the final UI is implemented
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.titleText.text = item.title
            holder.authorText.text = item.author
            holder.dateText.text = item.formattedDate()
            holder.commentCountText.text = "${item.commentCount} comments"
            holder.readStatusText.text = if (item.read) "Read" else "Unread"
            item.thumbnail?.let { uri ->
                Glide.with(holder.itemView).load(uri).into(holder.image)
            }
        }
    }

}
