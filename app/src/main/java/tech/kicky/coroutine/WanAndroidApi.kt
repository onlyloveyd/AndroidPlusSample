package tech.kicky.coroutine

import retrofit2.http.GET

/**
 * Wan Android API
 * author: yidong
 * 2021/1/2
 */
interface WanAndroidApi {

    @GET("/banner/json")
    suspend fun banners(): WanAndroidRoot<Banner>

    @GET("/hotkey/json")
    suspend fun hotKeys():WanAndroidRoot<HotKey>
}