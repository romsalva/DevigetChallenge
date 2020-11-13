package com.deviget.reddiget.data.room.converter

import android.net.Uri
import androidx.room.TypeConverter
import com.deviget.reddiget.presentation.extension.asUri
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromStringToUri(value: String?): Uri? {
        return value?.asUri()
    }

    @TypeConverter
    fun uriToStrng(uri: Uri?): String? {
        return uri?.toString()
    }
}