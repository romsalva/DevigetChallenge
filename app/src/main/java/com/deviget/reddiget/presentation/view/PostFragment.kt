package com.deviget.reddiget.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.presentation.util.formattedDate
import com.deviget.reddiget.presentation.util.formattedUsername
import com.deviget.reddiget.presentation.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {

    private lateinit var views: Views
    private val viewModel by viewModels<PostViewModel>()
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

        viewModel.post.observe(viewLifecycleOwner) { post ->
            if (post != null) {
                views.apply {
                    titleText.text = post.title
                    if (post.type == Post.Type.IMAGE && post.link != null) {
                        Glide.with(view.context).load(post.link).into(image)
                    } else if (post.link != null) {
                        linkText.text = post.link.toString()
                    }
                    image.isVisible = post.type == Post.Type.IMAGE
                    linkText.isVisible = post.type != Post.Type.IMAGE && post.link != null
                    contentText.text = post.text.orEmpty()
                    contentText.isVisible = post.text != null
                    authorText.text = post.formattedUsername(view.context)
                    dateText.text = post.formattedDate()
                    commentCountText.text = "${post.commentCount}"
                    readStatusImage.isVisible = post.read
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
        val linkText: TextView,
        val image: ImageView,
        val contentText: TextView,
        val authorText: TextView,
        val dateText: TextView,
        val commentCountText: TextView,
        val readStatusImage: ImageView,
        val dismissButton: ImageButton
    ) {
        constructor(view: View) : this(
            view.findViewById(R.id.text_title),
            view.findViewById(R.id.text_link),
            view.findViewById(R.id.image),
            view.findViewById(R.id.text_content),
            view.findViewById(R.id.text_author),
            view.findViewById(R.id.text_date),
            view.findViewById(R.id.text_comment_count),
            view.findViewById(R.id.image_read_status),
            view.findViewById(R.id.button_dismiss),
        )
    }

}