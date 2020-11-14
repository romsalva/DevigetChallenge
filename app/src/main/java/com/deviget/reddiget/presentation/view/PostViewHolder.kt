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
import com.deviget.reddiget.presentation.util.formattedDate
import com.deviget.reddiget.presentation.util.formattedUsername

class PostViewHolder(
    itemView: View,
    private val onAction: (position: Int, action: PostAction) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val titleText: TextView = itemView.findViewById(R.id.text_title)
    private val authorText: TextView = itemView.findViewById(R.id.text_author)
    private val dateText: TextView = itemView.findViewById(R.id.text_date)
    private val commentCountText: TextView = itemView.findViewById(R.id.text_comment_count)
    private val readStatusImage: ImageView = itemView.findViewById(R.id.image_read_status)
    private val thumbnail: ImageView = itemView.findViewById(R.id.image_thumbnail)
    private val downloadableIcon: ImageView = itemView.findViewById(R.id.icon_downloadable)
    private val dismissButton: ImageButton = itemView.findViewById(R.id.button_dismiss)

    fun bind(post: Post) {
        titleText.text = post.title
        authorText.text = post.formattedUsername(itemView.context)
        dateText.text = post.formattedDate()
        commentCountText.text = "${post.commentCount}"
        readStatusImage.isVisible = post.read
        post.thumbnail?.let { uri ->
            Glide.with(itemView).load(uri).into(thumbnail)
        }
        downloadableIcon.isVisible = post.type == Post.Type.IMAGE
        thumbnail.isVisible = post.thumbnail != null
    }

    init {
        itemView.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onAction(adapterPosition, PostAction.Click)
            }
        }
        dismissButton.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onAction(adapterPosition, PostAction.Dismiss)
            }
        }
        thumbnail.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onAction(adapterPosition, PostAction.ClickThumbnail)
            }
        }
    }
}