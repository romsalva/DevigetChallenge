package com.deviget.reddiget.presentation.util

import android.content.Context
import android.text.format.DateUtils
import com.deviget.reddiget.R
import com.deviget.reddiget.data.datamodel.Post

private const val YEAR_IN_MILLIS = 1000L * 60L * 60L * 24L * 365L

fun Post.formattedDate(context: Context): String =
    DateUtils.getRelativeDateTimeString(context, date.time, 1000L, YEAR_IN_MILLIS, 0).toString()

fun Post.formattedUsername(context: Context) = context.resources.getString(R.string.post_username_format, author)
