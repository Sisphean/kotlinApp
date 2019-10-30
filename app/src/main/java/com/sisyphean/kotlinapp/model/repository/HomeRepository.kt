package com.sisyphean.kotlinapp.model.repository

import com.sisyphean.kotlinapp.model.api.RetrofitClient
import com.sisyphean.kotlinapp.model.bean.ArticleBean
import com.sisyphean.kotlinapp.model.bean.BannerBean
import com.sisyphean.kotlinapp.model.bean.PageInfo
import com.sisyphean.kotlinapp.model.bean.Response

class HomeRepository {

    suspend fun getBanners(): Response<List<BannerBean>> = RetrofitClient.service.banners()

    suspend fun getArticles(category: Int, page: Int, limit: Int): Response<PageInfo<ArticleBean>> = RetrofitClient.service.articles(category, page, limit)
}