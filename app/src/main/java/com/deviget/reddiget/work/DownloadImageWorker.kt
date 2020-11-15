package com.deviget.reddiget.work

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class DownloadImageWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    @Suppress("BlockingMethodInNonBlockingContext") //We are running in IO dispatcher
    override suspend fun doWork(): Result {
        val displayName = inputData.getString(KEY_FILENAME)
        val uriString = inputData.getString(KEY_URI)
        return if (uriString != null && displayName != null) {
            try {
                withContext(Dispatchers.IO) {
                    val bitmap = Glide.with(applicationContext)
                        .asBitmap()
                        .load(uriString)
                        .submit()
                        .get()
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    }

                    val resolver = applicationContext.contentResolver
                    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if (uri != null) {
                        resolver.openOutputStream(uri)?.use { outputStream ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                            outputStream.flush()
                            outputStream.close()
                        } ?: kotlin.run {
                            resolver.delete(uri, null, null)
                        }
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        } else {
            Result.failure()
        }
    }

    companion object {
        const val KEY_FILENAME = "filename"
        const val KEY_URI = "uri"
    }

}
