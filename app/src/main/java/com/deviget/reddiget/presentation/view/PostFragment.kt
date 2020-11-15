package com.deviget.reddiget.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.presentation.extension.toast
import com.deviget.reddiget.presentation.util.formattedDate
import com.deviget.reddiget.presentation.util.formattedUsername
import com.deviget.reddiget.presentation.viewmodel.PostViewModel
import com.deviget.reddiget.work.DownloadImageWorker
import dagger.hilt.android.AndroidEntryPoint

private const val PERMISSIONS_REQUEST_CODE = 1
private const val WORK_TAG_DOWNLOAD_IMAGE = "work_tag_download_image"

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
            views.emptyText.isVisible = post == null
            views.contentGroup.isVisible = post != null
            if (post != null) {
                views.apply {
                    titleText.text = post.title
                    if (post.type == Post.Type.IMAGE && post.link != null) {
                        Glide.with(view.context).load(post.link).into(image)
                        downloadButton.setOnClickListener {
                            tryDownloadImage()
                        }
                    } else if (post.link != null) {
                        linkText.text = post.link.toString()
                    }
                    image.isVisible = post.type == Post.Type.IMAGE
                    linkText.isVisible = post.type != Post.Type.IMAGE && post.link != null
                    contentText.text = post.text.orEmpty()
                    contentText.isVisible = post.text != null
                    authorText.text = post.formattedUsername(view.context)
                    dateText.text = post.formattedDate(requireContext())
                    commentCountText.text = "${post.commentCount}"
                    readStatusImage.isVisible = post.read
                    dismissButton.setOnClickListener {
                        viewModel.hide()
                        navController.navigateUp()
                    }
                    downloadButton.isVisible = post.type == Post.Type.IMAGE && post.link != null
                }
            }
        }
        if (savedInstanceState == null) {
            val args by navArgs<PostFragmentArgs>()
            viewModel.setId(args.postId)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            (permissions.toList() zip grantResults.toList()).toMap().let {
                if (it[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED &&
                    it[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                ) {
                    downloadImage()
                }
            }
        }
    }

    private fun tryDownloadImage() {
        if (
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            downloadImage()
        }
    }

    /**
     * This should actually be in a ViewModel, but I'm running out of time.
     */
    private fun downloadImage() {
        viewModel.post.value?.let { post ->
            if (post.link != null) {
                val workManager = WorkManager.getInstance(requireContext())
                workManager.enqueue(
                    OneTimeWorkRequestBuilder<DownloadImageWorker>()
                        .setInputData(
                            Data.Builder().apply {
                                putString(DownloadImageWorker.KEY_FILENAME, post.id)
                                putString(DownloadImageWorker.KEY_URI, post.link.toString())
                            }.build()
                        )
                        .addTag(WORK_TAG_DOWNLOAD_IMAGE)
                        .build()
                )
                val liveData = workManager.getWorkInfosByTagLiveData(WORK_TAG_DOWNLOAD_IMAGE)
                val observer = object : Observer<List<WorkInfo>> {
                    override fun onChanged(workInfos: List<WorkInfo>?) {
                        workInfos?.firstOrNull()?.let {
                            when (it.state) {
                                WorkInfo.State.SUCCEEDED -> {
                                    toast(getString(R.string.download_complete))
                                    liveData.removeObserver(this)
                                }
                                WorkInfo.State.FAILED -> toast(getString(R.string.generic_error_message))
                                else -> Unit
                            }
                        }
                    }
                }
                liveData.observe(viewLifecycleOwner, observer)
            } else {
                toast(getString(R.string.missing_download_link))
            }
        }
    }

    private class Views(
        val emptyText: TextView,
        val titleText: TextView,
        val linkText: TextView,
        val image: ImageView,
        val contentText: TextView,
        val authorText: TextView,
        val dateText: TextView,
        val commentCountText: TextView,
        val readStatusImage: ImageView,
        val dismissButton: ImageButton,
        val downloadButton: ImageButton,
        val contentGroup: Group
    ) {
        constructor(view: View) : this(
            view.findViewById(R.id.text_empty),
            view.findViewById(R.id.text_title),
            view.findViewById(R.id.text_link),
            view.findViewById(R.id.image),
            view.findViewById(R.id.text_content),
            view.findViewById(R.id.text_author),
            view.findViewById(R.id.text_date),
            view.findViewById(R.id.text_comment_count),
            view.findViewById(R.id.image_read_status),
            view.findViewById(R.id.button_dismiss),
            view.findViewById(R.id.button_download),
            view.findViewById(R.id.group_content)
        )
    }

}