package com.sisyphean.kotlinapp.model.api

import com.sisyphean.kotlinapp.model.bean.BannerBean
import com.sisyphean.kotlinapp.model.bean.Response
import com.sisyphean.kotlinapp.model.bean.LoginBean
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
}
