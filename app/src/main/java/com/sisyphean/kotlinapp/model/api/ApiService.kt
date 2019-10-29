package com.sisyphean.kotlinapp.model.api

import com.sisyphean.kotlinapp.model.bean.Response
import com.sisyphean.kotlinapp.model.bean.LoginBean
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
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

}
