package com.sisyphean.kotlinapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sisyphean.kotlinapp.base.BaseViewModel
import com.sisyphean.kotlinapp.model.api.WebSocketClient
import com.sisyphean.kotlinapp.model.bean.*
import com.sisyphean.kotlinapp.model.bean.enums.MsgType
import com.sisyphean.kotlinapp.model.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class HomeViewModel : BaseViewModel() {

    private val repository: HomeRepository by lazy { HomeRepository() }

    val msgData: MutableLiveData<List<Market>> = MutableLiveData()

    val msgChangeData: MutableLiveData<List<Market>> = MutableLiveData()

    val bannerData: MutableLiveData<List<BannerBean>> = MutableLiveData()

    val articleData: MutableLiveData<List<ArticleBean>> = MutableLiveData()

    private lateinit var mWebSocket: WebSocket

    fun getMarkets() {
        mWebSocket = WebSocketClient.create(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("HomeViewModel", "onOpen is called...")
                webSocket.send(Gson().toJson(WSRequest(MsgType.MARKET.value)))
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("HomeViewModel", "msg: " + text)
                val wsMarkets = Gson().fromJson<WSMarkets>(text, WSMarkets::class.java)

                msgData.postValue(wsMarkets.data)
                msgChangeData.postValue(wsMarkets.changeData)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("HomeViewModel", "onClosed is called...code:${code} reason:${reason}")
            }
        })
    }

    fun getBanner() {
        viewModelScope.launch(Dispatchers.Default) {

            val response = repository.getBanners()

            withContext(Dispatchers.Main) {
                bannerData.value = response.data
            }
        }
    }

    fun getArticles() {
        viewModelScope.launch(Dispatchers.Default) {
            val response = repository.getArticles(1, 1, 3)

            withContext(Dispatchers.Main) {
                articleData.value = response.data.list
            }
        }
    }


    fun closeWebSocket() {
        mWebSocket.close(1000, "close normal")
    }
}