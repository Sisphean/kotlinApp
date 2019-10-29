package com.sisyphean.kotlinapp.model.repository

import com.sisyphean.kotlinapp.model.api.RetrofitClient
import com.sisyphean.kotlinapp.model.bean.BannerBean
import com.sisyphean.kotlinapp.model.bean.Response

class HomeRepository {

    suspend fun getBanners() : Response<List<BannerBean>> = RetrofitClient.service.banners()
}