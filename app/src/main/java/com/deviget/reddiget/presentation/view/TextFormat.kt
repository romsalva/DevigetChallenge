package com.deviget.reddiget.presentation.view

import com.deviget.reddiget.data.datamodel.Post
import java.text.SimpleDateFormat
import java.util.*

fun Post.formattedDate() = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(this.date)