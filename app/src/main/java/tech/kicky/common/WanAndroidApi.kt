package tech.kicky.common

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import tech.kicky.coroutine.*

/**
 * Wan Android API
 * author: yidong
 * 2021/1/2
 */
interface WanAndroidApi {

    @GET("/banner/json")
    suspend fun banners(): WanAndroidRoot<List<Banner>>

    @GET("/hotkey/json")
    suspend fun hotKeys(): WanAndroidRoot<List<HotKey>>

    @POST("/article/query/{pageNum}/json")
    suspend fun searchArticles(
        @Path("pageNum") pageNum: Int,
        @Query("k") key: String
    ): WanAndroidRoot<PageRoot<Article>>
}