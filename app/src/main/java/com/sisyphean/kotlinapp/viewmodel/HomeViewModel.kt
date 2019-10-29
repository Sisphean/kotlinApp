package com.sisyphean.kotlinapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sisyphean.kotlinapp.base.BaseViewModel
import com.sisyphean.kotlinapp.model.api.WebSocketClient
import com.sisyphean.kotlinapp.model.bean.WSMarkets
import com.sisyphean.kotlinapp.model.bean.WSRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class HomeViewModel : BaseViewModel() {

    val msgData: MutableLiveData<WSMarkets> = MutableLiveData()

    lateinit var mWebSocket: WebSocket

    fun getTicker() {
        mWebSocket = WebSocketClient.create(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                webSocket.send(Gson().toJson(WSRequest(4)))
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("HomeViewModel", "msg: " + text)
                val wsMarkets = Gson().fromJson<WSMarkets>(text, WSMarkets::class.java)
                msgData.postValue(wsMarkets)
            }
        })
    }

    fun closeWebSocket() {
        mWebSocket.close(1000, "")
    }
}