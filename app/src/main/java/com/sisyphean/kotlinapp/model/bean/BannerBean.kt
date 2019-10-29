package com.sisyphean.kotlinapp.model.bean

data class BannerBean(val bannerId: Int,
                      val bannerImg: String,
                      val langId: Int,
                      val sort: Int,
                      val status: Int,
                      val url: String)