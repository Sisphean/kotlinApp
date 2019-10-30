package com.sisyphean.kotlinapp.model.api

import com.sisyphean.kotlinapp.model.bean.*
import retrofit2.http.*
import java.util.*

interface ApiService {

    companion object {
        const val BASE_URL = "http://223.247.152.64:8081"
        const val BASE_WS_URL ="ws://223.247.152.64:8081/websocket"
    }

    @FormUrlEncoded
    @POST("/login")
    suspend fun login(@Field("account") accoint: String,
                      @Field("password") password: String): Response<LoginBean>

    @GET("/banners")
    suspend fun banners() : Response<List<BannerBean>>

    @GET("/bbs/articles/{category}")
    suspend fun articles(@Path("category") category: Int,
                         @Query("page") page: Int,
                         @Query("limit") limit: Int) : Response<PageInfo<ArticleBean>>
}
