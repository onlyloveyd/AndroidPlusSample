package tech.kicky.common

import okhttp3.ResponseBody
import retrofit2.http.*
import tech.kicky.coroutine.*

/**
 * Download Video API
 * author: yidong
 * 2021/1/2
 */
interface DownloadApi {
    @GET
    suspend fun downloadVideo(@Url url: String): ResponseBody
}