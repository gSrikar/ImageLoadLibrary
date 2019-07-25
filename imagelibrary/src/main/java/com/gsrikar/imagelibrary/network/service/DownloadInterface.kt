package com.gsrikar.imagelibrary.network.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Retrofit service
 */
interface DownloadInterface {

    @GET
    suspend fun downloadImage(@Url url: String): ResponseBody

}