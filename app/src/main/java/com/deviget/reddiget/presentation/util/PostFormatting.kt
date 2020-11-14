package com.deviget.reddiget.presentation.util

import android.content.Context
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post
import java.text.SimpleDateFormat
import java.util.*

fun Post.formattedDate() = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(this.date)
fun Post.formattedUsername(context: Context) = context.resources.getString(R.string.post_username_format, author)
