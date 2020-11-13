package com.deviget.reddiget.data.datamodel

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "post")
data class Post(
    @PrimaryKey
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
