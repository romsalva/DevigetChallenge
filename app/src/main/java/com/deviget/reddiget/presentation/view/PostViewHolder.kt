package com.deviget.reddiget.presentation.view

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deviget.reddiget.R

class PostViewHolder(
    itemView: View,
    private val onClick: (position: Int) -> Unit,
    private val onDismiss: (position: Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    val titleText: TextView = itemView.findViewById(R.id.text_title)
    val authorText: TextView = itemView.findViewById(R.id.text_author)
    val dateText: TextView = itemView.findViewById(R.id.text_date)
    val commentCountText: TextView = itemView.findViewById(R.id.text_comment_count)
    val readStatusText: TextView = itemView.findViewById(R.id.text_read_status)
    val image: ImageView = itemView.findViewById(R.id.image)
    val dismissButton: Button = itemView.findViewById(R.id.button_dismiss)

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