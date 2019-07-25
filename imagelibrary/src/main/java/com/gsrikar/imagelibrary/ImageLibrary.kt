package com.gsrikar.imagelibrary

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.gsrikar.imagelibrary.init.backgroundExecutor
import com.gsrikar.imagelibrary.init.mainThreadExecutor
import com.gsrikar.imagelibrary.network.downloadInterface
import com.gsrikar.imagelibrary.providers.ImageProvider.Companion.appContext
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


/**
 * Starting point to the library
 */
class ImageLibrary {

    companion object {
        private val TAG = ImageLibrary::class.java.simpleName
        private val DBG = BuildConfig.DEBUG
    }

    /**
     * Hash Map contains the image position and the path its byte data is stored
     */
    private val imageHashMap = hashMapOf<Int, String>()

    /**
     * List of positions that are recycled
     */
    private val apiCallHashMap = hashMapOf<Int, Job>()

    /**
     * Load the image
     */
    fun loadImage(
        imageView: AppCompatImageView,
        url: String,
        position: Int
    ) {
        backgroundExecutor.execute {
            if (DBG) Log.d(TAG, "Position: $position")
            loadImageCache(url, imageView, position)
        }
    }

    /**
     * View Position was recycled
     */
    fun recycledView(recycledPosition: Int) {
        if (DBG) Log.d(TAG, "Recycled Position: $recycledPosition")
        apiCallHashMap[recycledPosition]?.cancel()
    }

    private fun downloadImage(
        url: String,
        imageView: AppCompatImageView,
        position: Int
    ) {
        val imageJob = GlobalScope.launch(Dispatchers.IO) {
            // Make an api call to download the image
            val responseBody = requestImage(url)
            backgroundExecutor.execute {
                // Remove the job
                apiCallHashMap.remove(position)
                // Create a bitmap with the byte array
                createBitmap(responseBody.bytes(), imageView, position)
            }
        }
        // Add the api call to a hash map
        apiCallHashMap[position] = imageJob
    }

    /**
     * Make an api call and download the image
     */
    private suspend fun requestImage(url: String) =
        withContext(Dispatchers.IO) {
            downloadInterface.downloadImage(url)
        }

    private fun loadImageCache(
        url: String,
        imageView: AppCompatImageView,
        position: Int
    ) {
        if (imageHashMap.containsKey(position)) {
            if (DBG) Log.d(TAG, "Load the image from the cache")
            setImageBitmap(readCache(imageHashMap[position]!!), imageView)
        } else {
            downloadImage(url, imageView, position)
        }
    }

    private fun createBitmap(
        data: ByteArray,
        imageView: AppCompatImageView,
        position: Int
    ) {
        if (DBG) Log.d(TAG, "Create a bitmap")
        setImageBitmap(data, imageView)
        saveCache(position, data)
    }

    private fun setImageBitmap(
        data: ByteArray,
        imageView: AppCompatImageView
    ) {
        if (DBG) Log.d(TAG, "Set a bitmap")
        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        mainThreadExecutor.execute {
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun updateImageHashMap(position: Int, url: String) {
        if (DBG) Log.d(TAG, "Update Image Hash Map")
        imageHashMap[position] = url
    }

    private fun saveCache(position: Int, data: ByteArray) {
        if (DBG) Log.d(TAG, "Save Cache")
        appContext?.let {
            val file = createCacheFile(it)
            val outputStream = FileOutputStream(file)
            outputStream.write(data)

            updateImageHashMap(position, file.path)
        }
    }

    private fun readCache(path: String): ByteArray {
        if (DBG) Log.d(TAG, "Read Cache")
        val inputSteam = FileInputStream(path)
        return inputSteam.readBytes()
    }

    private fun createCacheFile(context: Context): File {
        if (DBG) Log.d(TAG, "Create a cache file")
        val file = File(context.cacheDir.path)
        if (!file.exists()) {
            file.mkdirs()
            file.createNewFile()
        }
        val fileName = "image-file-${System.currentTimeMillis()}-cache.txt"
        val cacheFile = File(file.path, fileName)
        if (!cacheFile.exists()) {
            cacheFile.createNewFile()
        }
        if (DBG) Log.d(TAG, "Create cache file at ${cacheFile.path}")
        return cacheFile
    }

}