package com.example.tpproject

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object Functions {

    fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    fun saveImageToExternalStorage(context: Context, bitmap: Bitmap): String {
        val bytes = convertBitmapToByteArray(bitmap)
        val fileName = "image_${System.currentTimeMillis()}.png"
        val filePath = context.externalCacheDir?.absolutePath + File.separator + fileName

        try {
            val fileOutputStream = FileOutputStream(filePath)
            fileOutputStream.write(bytes)
            fileOutputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return filePath
    }




}