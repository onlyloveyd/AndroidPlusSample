package tech.kicky.common

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit instance
 * author: yidong
 * 2021/1/2
 */
object Retrofitance {
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofitance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val wanAndroidApi: WanAndroidApi by lazy {
        retrofitance.create(WanAndroidApi::class.java)
    }

    val downloadApi: DownloadApi by lazy {
        retrofitance.create(DownloadApi::class.java)
    }
}