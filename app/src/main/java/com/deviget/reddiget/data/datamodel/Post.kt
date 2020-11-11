package com.deviget.reddiget.data.datamodel

import android.net.Uri
import java.util.*

data class Post(
    val id: String,
    val title: String,
    val author: String,
    val date: Date,
    val thumbnail: Uri?,
    val commentCount: Int,
    val read: Boolean,
    val hidden: Boolean,
    val text: String?,
    val link: Uri?
)
