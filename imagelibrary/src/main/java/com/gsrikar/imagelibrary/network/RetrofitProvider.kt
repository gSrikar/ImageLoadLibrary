package com.gsrikar.imagelibrary.network

import com.gsrikar.imagelibrary.network.service.DownloadInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.gsrikar.com/")
    .client(okHttpClient)
    .build()

var downloadInterface: DownloadInterface = retrofit.create(DownloadInterface::class.java)
