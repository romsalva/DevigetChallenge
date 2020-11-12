package com.deviget.reddiget.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post


class PostsAdapter(
    private val onPostClicked: (post: Post, position: Int) -> Unit = { _, _ -> },
    private val onPostDismissed: (post: Post, position: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<PostViewHolder>() {

    private val posts: MutableList<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false),
            onClick = { position -> onPostClicked(posts[position], position) },
            onDismiss = { position -> onPostDismissed(posts[position], position) }
        )

    //TODO: hardcoded strings will be fixed when the final UI is implemented
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = posts[position]
        holder.titleText.text = item.text
        holder.authorText.text = item.author
        holder.dateText.text = item.formattedDate()
        holder.commentCountText.text = "${item.commentCount} comments"
        holder.readStatusText.text = if (item.read) "Read" else "Unread"
        item.thumbnail?.let { uri ->
            Glide.with(holder.itemView).load(uri).into(holder.image)
        }
    }

    override fun getItemCount(): Int = posts.size

    fun setPosts(posts: List<Post>) {
        //TODO: This will go when pagination is implemented
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

}
