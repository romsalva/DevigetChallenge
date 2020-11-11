package com.deviget.reddiget

import android.net.Uri

object Configuration {
    val redditUri = Uri.Builder().scheme("https").authority("api.reddit.com").build()
}