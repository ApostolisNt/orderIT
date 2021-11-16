package com.example.orderit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

internal object OkhttpSingleton {
    private var ourInstance: OkHttpClient? = null

    @Synchronized
    @JvmStatic
    fun getInstance(): OkHttpClient {
        if (ourInstance == null) ourInstance = OkHttpClient()
        return ourInstance!!
    }

    private val MEDIA_TYPE_MARKDOWN: MediaType = "text/x-markdown; charset=utf-8".toMediaTypeOrNull()!!

    @JvmStatic
    @Throws(Exception::class)
    fun run(postBody: String) = GlobalScope.launch(Dispatchers.IO) 
    {
            val request = Request.Builder()
                    .url("http://192.168.88.237:443")
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                    .build()
            getInstance().newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                println(response.body!!.string())
            }
        }
}