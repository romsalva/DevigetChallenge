package com.deviget.reddiget.presentation.view

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post

class PostViewHolder(
    itemView: View,
    private val onClick: (position: Int) -> Unit,
    private val onDismiss: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val titleText: TextView = itemView.findViewById(R.id.text_title)
    private val authorText: TextView = itemView.findViewById(R.id.text_author)
    private val dateText: TextView = itemView.findViewById(R.id.text_date)
    private val commentCountText: TextView = itemView.findViewById(R.id.text_comment_count)
    private val readStatusText: ImageView = itemView.findViewById(R.id.image_read_status)
    private val thumbnail: ImageView = itemView.findViewById(R.id.image_thumbnail)
    private val dismissButton: ImageButton = itemView.findViewById(R.id.button_dismiss)

    fun bind(post: Post) {
        titleText.text = post.title
        authorText.text = "u/${post.author}"
        dateText.text = post.formattedDate()
        commentCountText.text = "${post.commentCount}"
        readStatusText.isVisible = post.read
        post.thumbnail?.let { uri ->
            Glide.with(itemView).load(uri).into(thumbnail)
        }
        thumbnail.isVisible = post.thumbnail != null
    }

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onClick(adapterPosition)
            }
        }
        dismissButton.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onDismiss(adapterPosition)
            }
        }
    }
}