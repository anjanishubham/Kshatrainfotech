package com.example.kshatrainfotech.common

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

import java.io.InputStream





object Utils {

    fun getUri(context: Context, child: String, authority: String): Uri {
        val image = File(context.filesDir, child)
        return FileProvider.getUriForFile(
            context,
            authority,
            image
        )
    }

    fun calculateAspectRatio(imageUri: Uri,context: Context): Float {
        try {
            // Open an input stream for the image URI
            val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            val imageWidth = options.outWidth
            val imageHeight = options.outHeight
            val aspectRatio = imageWidth.toFloat() / imageHeight

            // Close the input stream
            inputStream?.close()
            return aspectRatio
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return 1f
    }
}