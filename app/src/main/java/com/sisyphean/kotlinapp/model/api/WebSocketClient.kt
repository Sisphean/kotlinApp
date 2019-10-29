package com.sisyphean.kotlinapp.model.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

object WebSocketClient {

    private val client by lazy {
        OkHttpClient.Builder().build()
    }

    private val request by lazy {
        Request.Builder().url(ApiService.BASE_WS_URL).build()
    }

    fun create(listener: WebSocketListener): WebSocket {
        //建立连接
        return client.newWebSocket(request, listener)
    }
}