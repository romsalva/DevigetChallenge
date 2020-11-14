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
    val type: Type,
    val text: String?,
    val link: Uri?
) {
    enum class Type(val value: Int) {
        OTHER(-1),
        IMAGE(0),
        LINK(1);

        companion object {
            fun fromValue(value: Int) = when (value) {
                0 -> IMAGE
                1 -> LINK
                else -> OTHER
            }
        }

    }
}
