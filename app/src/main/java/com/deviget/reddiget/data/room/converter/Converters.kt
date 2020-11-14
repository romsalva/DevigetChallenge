package com.deviget.reddiget.data.room.converter

import android.net.Uri
import androidx.room.TypeConverter
import com.deviget.reddiget.data.datamodel.Post
import com.deviget.reddiget.presentation.extension.asUri
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromStringToUri(value: String?): Uri? = value?.asUri()

    @TypeConverter
    fun uriToStrng(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    fun fromIntToPostType(value: Int?): Post.Type? = value?.let { Post.Type.fromValue(value) } ?: Post.Type.OTHER

    @TypeConverter
    fun fromPostTypeToInt(type: Post.Type?): Int? = type?.value
}