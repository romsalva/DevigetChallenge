package com.deviget.reddiget.presentation.extension

import android.net.Uri

fun String.asUri(): Uri = Uri.parse(this)