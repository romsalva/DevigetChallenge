package com.deviget.reddiget.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deviget.reddiget.R
import com.deviget.reddiget.presentation.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {

    private lateinit var views: Views
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views = Views(view)
        val viewModel by viewModels<PostViewModel>()
        viewModel.post.observe(viewLifecycleOwner) { post ->
            if (post != null) {
                views.apply {
                    titleText.text = post.title
                    post.link?.let { uri ->
                        Glide.with(view.context).load(uri).into(image)
                    }
                    image.isVisible = post.link != null
                    authorText.text = post.author
                    dateText.text = post.formattedDate()
                    commentCountText.text = "${post.commentCount} comments"
                    readStatusText.text = if (post.read) "Read" else "Unread"
                    dismissButton.setOnClickListener {
                        viewModel.hide()
                        navController.navigateUp()
                    }
                }
            } else {
                //TODO: add "empty" view to fragment_posts
            }
        }
        if (savedInstanceState == null) {
            val args by navArgs<PostFragmentArgs>()
            viewModel.setId(args.postId)
        }
    }

    private class Views(
        val titleText: TextView,
        val image: ImageView,
        val authorText: TextView,
        val dateText: TextView,
        val commentCountText: TextView,
        val readStatusText: TextView,
        val dismissButton: Button
    ) {
        constructor(view: View) : this(
            view.findViewById(R.id.text_title),
            view.findViewById(R.id.image),
            view.findViewById(R.id.text_author),
            view.findViewById(R.id.text_date),
            view.findViewById(R.id.text_comment_count),
            view.findViewById(R.id.text_read_status),
            view.findViewById(R.id.button_dismiss),
        )
    }

}