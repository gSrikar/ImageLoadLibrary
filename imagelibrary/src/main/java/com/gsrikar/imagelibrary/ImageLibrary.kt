package com.gsrikar.imagelibrary

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.gsrikar.imagelibrary.init.backgroundExecutor
import com.gsrikar.imagelibrary.init.mainThreadExecutor
import com.gsrikar.imagelibrary.network.downloadInterface
import com.gsrikar.imagelibrary.providers.ImageProvider.Companion.appContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


/**
 * Starting point to the library
 */
class ImageLibrary {

    companion object {
        private val TAG = ImageLibrary::class.java.simpleName
    }

    /**
     * Hash Map contains the image position and the path its byte data is stored
     */
    private val imageHashMap = hashMapOf<Int, String>()

    /**
     * Load the image
     */
    fun loadImage(
        imageView: AppCompatImageView,
        url: String,
        position: Int
    ) {
        backgroundExecutor.execute {
            Log.d(TAG, "Position: $position")
            loadImageCache(url, imageView, position)
        }
    }

    private fun downloadImage(
        url: String,
        imageView: AppCompatImageView,
        position: Int
    ) {
        downloadInterface.downloadImage(url).enqueue(
            object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    backgroundExecutor.execute {
                        Log.e(TAG, "Retrofit Api failed: ${t.message}")
                    }
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    backgroundExecutor.execute {
                        receivedResponse(response, imageView, position, url)
                    }
                }
            }
        )
    }

    private fun loadImageCache(
        url: String,
        imageView: AppCompatImageView,
        position: Int
    ) {
        if (imageHashMap.containsKey(position)) {
            Log.d(TAG, "Load the image from the cache")
            setImageBitmap(readCache(imageHashMap[position]!!), imageView)
        } else {
            downloadImage(url, imageView, position)
        }
    }

    private fun receivedResponse(
        response: Response<ResponseBody>,
        imageView: AppCompatImageView,
        position: Int,
        url: String
    ) {
        if (response.isSuccessful) {
            response.body()?.let {
                Log.d(TAG, "Response is successful for $url")
                createBitmap(it.bytes(), imageView, position)
            }
        } else {
            Log.e(TAG, "Image Download Failed: ${response.message()}")
        }
    }

    private fun createBitmap(
        data: ByteArray,
        imageView: AppCompatImageView,
        position: Int
    ) {
        Log.d(TAG, "Create a bitmap")
        setImageBitmap(data, imageView)
        saveCache(position, data)
    }

    private fun setImageBitmap(
        data: ByteArray,
        imageView: AppCompatImageView
    ) {
        Log.d(TAG, "Set a bitmap")
        val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
        mainThreadExecutor.execute {
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun updateImageHashMap(position: Int, url: String) {
        Log.d(TAG, "Update Image Hash Map")
        imageHashMap[position] = url
    }

    private fun saveCache(position: Int, data: ByteArray) {
        Log.d(TAG, "Save Cache")
        appContext?.let {
            val file = createCacheFile(it)
            val outputStream = FileOutputStream(file)
            outputStream.write(data)

            updateImageHashMap(position, file.path)
        }
    }

    private fun readCache(path: String): ByteArray {
        Log.d(TAG, "Read Cache")
        val inputSteam = FileInputStream(path)
        return inputSteam.readBytes()
    }

    private fun createCacheFile(context: Context): File {
        Log.d(TAG, "Create a cache file")
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
        Log.d(TAG, "Create cache file at ${cacheFile.path}")
        return cacheFile
    }

}